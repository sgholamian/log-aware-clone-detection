//,temp,VectorMapJoinInnerLongOperator.java,129,390,temp,VectorMapJoinOuterStringOperator.java,121,406
//,3
public class xxx {
  @Override
  public void processBatch(VectorizedRowBatch batch) throws HiveException {

    try {

      // Do the per-batch setup for an inner join.

      innerPerBatchSetup(batch);

      // For inner joins, we may apply the filter(s) now.
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
       * Single-Column Long specific declarations.
       */

      // The one join column for this specialized class.
      LongColumnVector joinColVector = (LongColumnVector) batch.cols[singleJoinColumn];
      long[] vector = joinColVector.vector;

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
         * Single-Column Long specific repeated lookup.
         */

        JoinUtil.JoinResult joinResult;
        if (!joinColVector.noNulls && joinColVector.isNull[0]) {
          joinResult = JoinUtil.JoinResult.NOMATCH;
        } else {
          long key = vector[0];
          if (useMinMax && (key < min || key > max)) {
            // Out of range for whole batch.
            joinResult = JoinUtil.JoinResult.NOMATCH;
          } else {
            joinResult = hashMap.lookup(key, hashMapResults[0]);
          }
        }

        /*
         * Common repeated join result processing.
         */

        if (LOG.isDebugEnabled()) {
          LOG.debug(CLASS_NAME + " batch #" + batchCounter + " repeated joinResult " + joinResult.name());
        }
        finishInnerRepeated(batch, joinResult, hashMapResults[0]);
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

        int hashMapResultCount = 0;
        int allMatchCount = 0;
        int equalKeySeriesCount = 0;
        int spillCount = 0;

        /*
         * Single-Column Long specific variables.
         */

        long saveKey = 0;

        // We optimize performance by only looking up the first key in a series of equal keys.
        boolean haveSaveKey = false;
        JoinUtil.JoinResult saveJoinResult = JoinUtil.JoinResult.NOMATCH;

        // Logical loop over the rows in the batch since the batch may have selected in use.
        for (int logical = 0; logical < inputLogicalSize; logical++) {
          int batchIndex = (selectedInUse ? selected[logical] : logical);

          /*
           * Single-Column Long get key.
           */

          long currentKey;
          boolean isNull;
          if (!joinColVector.noNulls && joinColVector.isNull[batchIndex]) {
            currentKey = 0;
            isNull = true;
          } else {
            currentKey = vector[batchIndex];
            isNull = false;
          }

          /*
           * Equal key series checking.
           */

          if (isNull || !haveSaveKey || currentKey != saveKey) {

            // New key.

            if (haveSaveKey) {
              // Move on with our counts.
              switch (saveJoinResult) {
              case MATCH:
                hashMapResultCount++;
                equalKeySeriesCount++;
                break;
              case SPILL:
                hashMapResultCount++;
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
               * Single-Column Long specific save key.
               */
  
              saveKey = currentKey;
  
              /*
               * Single-Column Long specific lookup key.
               */
  
              if (useMinMax && (currentKey < min || currentKey > max)) {
                // Key out of range for whole hash table.
                saveJoinResult = JoinUtil.JoinResult.NOMATCH;
              } else {
                saveJoinResult = hashMap.lookup(currentKey, hashMapResults[hashMapResultCount]);
              }
            }

            /*
             * Common inner join result processing.
             */

            switch (saveJoinResult) {
            case MATCH:
              equalKeySeriesHashMapResultIndices[equalKeySeriesCount] = hashMapResultCount;
              equalKeySeriesAllMatchIndices[equalKeySeriesCount] = allMatchCount;
              equalKeySeriesIsSingleValue[equalKeySeriesCount] = hashMapResults[hashMapResultCount].isSingleRow();
              equalKeySeriesDuplicateCounts[equalKeySeriesCount] = 1;
              allMatchs[allMatchCount++] = batchIndex;
              // VectorizedBatchUtil.debugDisplayOneRow(batch, batchIndex, CLASS_NAME + " MATCH isSingleValue " + equalKeySeriesIsSingleValue[equalKeySeriesCount] + " currentKey " + currentKey);
              break;

            case SPILL:
              spills[spillCount] = batchIndex;
              spillHashMapResultIndices[spillCount] = hashMapResultCount;
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
              equalKeySeriesDuplicateCounts[equalKeySeriesCount]++;
              allMatchs[allMatchCount++] = batchIndex;
              // VectorizedBatchUtil.debugDisplayOneRow(batch, batchIndex, CLASS_NAME + " MATCH duplicate");
              break;

            case SPILL:
              spills[spillCount] = batchIndex;
              spillHashMapResultIndices[spillCount] = hashMapResultCount;
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
            hashMapResultCount++;
            equalKeySeriesCount++;
            break;
          case SPILL:
            hashMapResultCount++;
            break;
          case NOMATCH:
            break;
          }
        }

        if (LOG.isDebugEnabled()) {
          LOG.debug(CLASS_NAME +
              " allMatchs " + intArrayToRangesString(allMatchs,allMatchCount) +
              " equalKeySeriesHashMapResultIndices " + intArrayToRangesString(equalKeySeriesHashMapResultIndices, equalKeySeriesCount) +
              " equalKeySeriesAllMatchIndices " + intArrayToRangesString(equalKeySeriesAllMatchIndices, equalKeySeriesCount) +
              " equalKeySeriesIsSingleValue " + Arrays.toString(Arrays.copyOfRange(equalKeySeriesIsSingleValue, 0, equalKeySeriesCount)) +
              " equalKeySeriesDuplicateCounts " + Arrays.toString(Arrays.copyOfRange(equalKeySeriesDuplicateCounts, 0, equalKeySeriesCount)) +
              " spills " + intArrayToRangesString(spills, spillCount) +
              " spillHashMapResultIndices " + intArrayToRangesString(spillHashMapResultIndices, spillCount) +
              " hashMapResults " + Arrays.toString(Arrays.copyOfRange(hashMapResults, 0, hashMapResultCount)));
        }

        finishInner(batch,
            allMatchCount, equalKeySeriesCount, spillCount, hashMapResultCount);
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