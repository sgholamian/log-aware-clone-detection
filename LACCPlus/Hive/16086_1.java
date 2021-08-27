//,temp,VectorMapJoinLeftSemiStringOperator.java,121,366,temp,VectorMapJoinInnerBigOnlyLongOperator.java,131,394
//,3
public class xxx {
  @Override
  public void processBatch(VectorizedRowBatch batch) throws HiveException {

    try {

      // Do the per-batch setup for an left semi join.

      // (Currently none)
      // leftSemiPerBatchSetup(batch);

      // For left semi joins, we may apply the filter(s) now.
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

      /*
       * Single-Column String specific declarations.
       */

      // The one join column for this specialized class.
      BytesColumnVector joinColVector = (BytesColumnVector) batch.cols[singleJoinColumn];
      byte[][] vector = joinColVector.vector;
      int[] start = joinColVector.start;
      int[] length = joinColVector.length;

      /*
       * Single-Column Long check for repeating.
       */

      // Check single column for repeating.
      boolean allKeyInputColumnsRepeating = joinColVector.isRepeating;

      if (allKeyInputColumnsRepeating) {

        /*
         * Repeating.
         */

        // All key input columns are repeating.  Generate key once.  Lookup once.
        // Since the key is repeated, we must use entry 0 regardless of selectedInUse.

        /*
         * Single-Column String specific repeated lookup.
         */

        JoinUtil.JoinResult joinResult;
        if (!joinColVector.noNulls && joinColVector.isNull[0]) {
          joinResult = JoinUtil.JoinResult.NOMATCH;
        } else {
          byte[] keyBytes = vector[0];
          int keyStart = start[0];
          int keyLength = length[0];
          joinResult = hashSet.contains(keyBytes, keyStart, keyLength, hashSetResults[0]);
        }

        /*
         * Common repeated join result processing.
         */

        if (LOG.isDebugEnabled()) {
          LOG.debug(CLASS_NAME + " batch #" + batchCounter + " repeated joinResult " + joinResult.name());
        }
        finishLeftSemiRepeated(batch, joinResult, hashSetResults[0]);
      } else {

        /*
         * NOT Repeating.
         */

        if (LOG.isDebugEnabled()) {
          LOG.debug(CLASS_NAME + " batch #" + batchCounter + " non-repeated");
        }

        // We remember any matching rows in matchs / matchSize.  At the end of the loop,
        // selected / batch.size will represent both matching and non-matching rows for outer join.
        // Only deferred rows will have been removed from selected.
        int selected[] = batch.selected;
        boolean selectedInUse = batch.selectedInUse;

        int hashSetResultCount = 0;
        int allMatchCount = 0;
        int spillCount = 0;

        /*
         * Single-Column String specific variables.
         */

        int saveKeyBatchIndex = -1;

        // We optimize performance by only looking up the first key in a series of equal keys.
        boolean haveSaveKey = false;
        JoinUtil.JoinResult saveJoinResult = JoinUtil.JoinResult.NOMATCH;

        // Logical loop over the rows in the batch since the batch may have selected in use.
        for (int logical = 0; logical < inputLogicalSize; logical++) {
          int batchIndex = (selectedInUse ? selected[logical] : logical);

          /*
           * Single-Column String get key.
           */

          // Implicit -- use batchIndex.
          boolean isNull = !joinColVector.noNulls && joinColVector.isNull[batchIndex];

          /*
           * Equal key series checking.
           */

          if (isNull || !haveSaveKey ||
              StringExpr.equal(vector[saveKeyBatchIndex], start[saveKeyBatchIndex], length[saveKeyBatchIndex],
                                 vector[batchIndex], start[batchIndex], length[batchIndex]) == false) {

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
              saveJoinResult = JoinUtil.JoinResult.NOMATCH;
              haveSaveKey = false;
            } else {
              // Regardless of our matching result, we keep that information to make multiple use
              // of it for a possible series of equal keys.
              haveSaveKey = true;
  
              /*
               * Single-Column String specific save key and lookup.
               */
  
              saveKeyBatchIndex = batchIndex;
  
              /*
               * Single-Column String specific lookup key.
               */
  
              byte[] keyBytes = vector[batchIndex];
              int keyStart = start[batchIndex];
              int keyLength = length[batchIndex];
              saveJoinResult = hashSet.contains(keyBytes, keyStart, keyLength, hashSetResults[hashSetResultCount]);
            }

            /*
             * Common left-semi join result processing.
             */

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

        finishLeftSemi(batch,
            allMatchCount, spillCount,
            (VectorMapJoinHashTableResult[]) hashSetResults);
      }

      if (batch.size > 0) {
        // Forward any remaining selected rows.
        forwardBigTableBatch(batch);
      }

    } catch (IOException e) {
      throw new HiveException(e);
    } catch (Exception e) {
      throw new HiveException(e);
    }
  }

};