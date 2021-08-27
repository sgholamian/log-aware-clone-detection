//,temp,VectorMapJoinLeftSemiLongOperator.java,130,383,temp,VectorMapJoinAntiJoinLongOperator.java,105,306
//,3
public class xxx {
  @Override
  public void processBatch(VectorizedRowBatch batch) throws HiveException {

    try {
      // (Currently none)
      // antiPerBatchSetup(batch);

      // For anti joins, we may apply the filter(s) now.
      for(VectorExpression ve : bigTableFilterExpressions) {
        ve.evaluate(batch);
      }

      final int inputLogicalSize = batch.size;
      if (inputLogicalSize == 0) {
        return;
      }

      // Perform any key expressions.  Results will go into scratch columns.
      if (bigTableKeyExpressions != null) {
        for (VectorExpression ve : bigTableKeyExpressions) {
          ve.evaluate(batch);
        }
      }

      // The one join column for this specialized class.
      LongColumnVector joinColVector = (LongColumnVector) batch.cols[singleJoinColumn];
      long[] vector = joinColVector.vector;

      // Check single column for repeating.
      boolean allKeyInputColumnsRepeating = joinColVector.isRepeating;

      if (allKeyInputColumnsRepeating) {
        // All key input columns are repeating.  Generate key once.  Lookup once.
        // Since the key is repeated, we must use entry 0 regardless of selectedInUse.
        JoinUtil.JoinResult joinResult;
        if (!joinColVector.noNulls && joinColVector.isNull[0]) {
          // For anti join, if the right side is null then its a match.
          joinResult = JoinUtil.JoinResult.MATCH;
        } else {
          long key = vector[0];
          if (useMinMax && (key < min || key > max)) {
            // Out of range for whole batch. Its a match for anti join. We can emit the row.
            joinResult = JoinUtil.JoinResult.MATCH;
          } else {
            joinResult = hashSet.contains(key, hashSetResults[0]);
            // reverse the join result for anti join.
            joinResult = inverseResultForAntiJoin(joinResult);
          }
        }

        // Common repeated join result processing.
        if (LOG.isDebugEnabled()) {
          LOG.debug(CLASS_NAME + " batch #" + batchCounter + " repeated joinResult " + joinResult.name());
        }
        finishAntiRepeated(batch, joinResult, hashSetResults[0]);
      } else {
        // NOT Repeating.

        if (LOG.isDebugEnabled()) {
          LOG.debug(CLASS_NAME + " batch #" + batchCounter + " non-repeated");
        }

        // We remember any matching rows in matches / matchSize.  At the end of the loop,
        // selected / batch.size will represent both matching and non-matching rows for outer join.
        // Only deferred rows will have been removed from selected.
        int selected[] = batch.selected;
        boolean selectedInUse = batch.selectedInUse;

        int hashSetResultCount = 0;
        int allMatchCount = 0;
        int spillCount = 0;
        long saveKey = 0;

        // We optimize performance by only looking up the first key in a series of equal keys.
        boolean haveSaveKey = false;
        JoinUtil.JoinResult saveJoinResult = JoinUtil.JoinResult.NOMATCH;

        // Logical loop over the rows in the batch since the batch may have selected in use.
        for (int logical = 0; logical < inputLogicalSize; logical++) {
          int batchIndex = (selectedInUse ? selected[logical] : logical);

          // Single-Column Long get key.
          long currentKey;
          boolean isNull;
          if (!joinColVector.noNulls && joinColVector.isNull[batchIndex]) {
            currentKey = 0;
            isNull = true;
          } else {
            currentKey = vector[batchIndex];
            isNull = false;
          }

          // Equal key series checking.
          if (isNull || !haveSaveKey || currentKey != saveKey) {
            // New key.
            if (haveSaveKey) {
              // Move on with our counts.
              switch (saveJoinResult) {
              case MATCH:
                // We have extracted the existence from the hash set result, so we don't keep it.
                break;
              case SPILL:
                // We keep the hash set result for its spill information.
                hashSetResultCount++;
                break;
              case NOMATCH:
                break;
              }
            }

            if (isNull) {
              saveJoinResult = JoinUtil.JoinResult.MATCH;
              haveSaveKey = false;
            } else {
              // Regardless of our matching result, we keep that information to make multiple use
              // of it for a possible series of equal keys.
              haveSaveKey = true;
              saveKey = currentKey;
              if (useMinMax && (currentKey < min || currentKey > max)) {
                saveJoinResult = JoinUtil.JoinResult.NOMATCH;
              } else {
                saveJoinResult = hashSet.contains(currentKey, hashSetResults[hashSetResultCount]);
              }

              // Reverse the match result for anti join.
              // Key out of range for whole hash table, is a valid match for anti join.
              saveJoinResult = inverseResultForAntiJoin(saveJoinResult);
            }

            // Common anti join result processing.
            switch (saveJoinResult) {
            case MATCH:
              allMatchs[allMatchCount++] = batchIndex;
              // VectorizedBatchUtil.debugDisplayOneRow(batch, batchIndex, CLASS_NAME + " MATCH isSingleValue " + equalKeySeriesIsSingleValue[equalKeySeriesCount] + " currentKey " + currentKey);
              break;

            case SPILL:
              spills[spillCount] = batchIndex;
              spillHashMapResultIndices[spillCount] = hashSetResultCount;
              spillCount++;
              break;

            case NOMATCH:
              // VectorizedBatchUtil.debugDisplayOneRow(batch, batchIndex, CLASS_NAME + " NOMATCH" + " currentKey " + currentKey);
              break;
            }
          } else {
            // Series of equal keys.
            switch (saveJoinResult) {
            case MATCH:
              allMatchs[allMatchCount++] = batchIndex;
              // VectorizedBatchUtil.debugDisplayOneRow(batch, batchIndex, CLASS_NAME + " MATCH duplicate");
              break;

            case SPILL:
              spills[spillCount] = batchIndex;
              spillHashMapResultIndices[spillCount] = hashSetResultCount;
              spillCount++;
              break;

            case NOMATCH:
              // VectorizedBatchUtil.debugDisplayOneRow(batch, batchIndex, CLASS_NAME + " NOMATCH duplicate");
              break;
            }
          }
        }

        if (haveSaveKey) {
          // Update our counts for the last key.
          switch (saveJoinResult) {
          case MATCH:
            // We have extracted the existence from the hash set result, so we don't keep it.
            break;
          case SPILL:
            // We keep the hash set result for its spill information.
            hashSetResultCount++;
            break;
          case NOMATCH:
            break;
          }
        }

        if (LOG.isDebugEnabled()) {
          LOG.debug(CLASS_NAME +
              " allMatchs " + intArrayToRangesString(allMatchs, allMatchCount) +
              " spills " + intArrayToRangesString(spills, spillCount) +
              " spillHashMapResultIndices " + intArrayToRangesString(spillHashMapResultIndices, spillCount) +
              " hashMapResults " + Arrays.toString(Arrays.copyOfRange(hashSetResults, 0, hashSetResultCount)));
        }

        finishAnti(batch, allMatchCount, spillCount, hashSetResults);
      }

      if (batch.size > 0) {
        // Forward any remaining selected rows.
        forwardBigTableBatch(batch);
      }

    } catch (Exception e) {
      throw new HiveException(e);
    }
  }

};