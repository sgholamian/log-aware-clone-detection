//,temp,VectorMapJoinLeftSemiMultiKeyOperator.java,136,395,temp,VectorMapJoinAntiJoinStringOperator.java,120,362
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
       * Multi-Key specific declarations.
       */

      // None.

      /*
       * Multi-Key Long check for repeating.
       */

      // If all BigTable input columns to key expressions are isRepeating, then
      // calculate key once; lookup once.
      boolean allKeyInputColumnsRepeating;
      if (bigTableKeyColumnMap.length == 0) {
       allKeyInputColumnsRepeating = false;
      } else {
        allKeyInputColumnsRepeating = true;
        for (int i = 0; i < bigTableKeyColumnMap.length; i++) {
          if (!batch.cols[bigTableKeyColumnMap[i]].isRepeating) {
            allKeyInputColumnsRepeating =  false;
            break;
          }
        }
      }

      if (allKeyInputColumnsRepeating) {

        /*
         * Repeating.
         */

        // All key input columns are repeating.  Generate key once.  Lookup once.
        // Since the key is repeated, we must use entry 0 regardless of selectedInUse.

        /*
         * Multi-Key specific repeated lookup.
         */

        keyVectorSerializeWrite.setOutput(currentKeyOutput);
        keyVectorSerializeWrite.serializeWrite(batch, 0);
        JoinUtil.JoinResult joinResult;
        if (keyVectorSerializeWrite.getHasAnyNulls()) {
          joinResult = JoinUtil.JoinResult.NOMATCH;
        } else {
          byte[] keyBytes = currentKeyOutput.getData();
          int keyLength = currentKeyOutput.getLength();
          // LOG.debug(CLASS_NAME + " processOp all " + displayBytes(keyBytes, 0, keyLength));
          joinResult = hashSet.contains(keyBytes, 0, keyLength, hashSetResults[0]);
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
         * Multi-Key specific variables.
         */

        Output temp;

        // We optimize performance by only looking up the first key in a series of equal keys.
        boolean haveSaveKey = false;
        JoinUtil.JoinResult saveJoinResult = JoinUtil.JoinResult.NOMATCH;

        // Logical loop over the rows in the batch since the batch may have selected in use.
        for (int logical = 0; logical < inputLogicalSize; logical++) {
          int batchIndex = (selectedInUse ? selected[logical] : logical);

          /*
           * Multi-Key get key.
           */

          // Generate binary sortable key for current row in vectorized row batch.
          keyVectorSerializeWrite.setOutput(currentKeyOutput);
          keyVectorSerializeWrite.serializeWrite(batch, batchIndex);
          boolean isAnyNull = keyVectorSerializeWrite.getHasAnyNulls();

          // LOG.debug(CLASS_NAME + " currentKey " +
          //      VectorizedBatchUtil.displayBytes(currentKeyOutput.getData(), 0, currentKeyOutput.getLength()));

          /*
           * Equal key series checking.
           */

          if (isAnyNull || !haveSaveKey || !saveKeyOutput.arraysEquals(currentKeyOutput)) {

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

            if (isAnyNull) {
              saveJoinResult = JoinUtil.JoinResult.NOMATCH;
              haveSaveKey = false;
            } else {
              // Regardless of our matching result, we keep that information to make multiple use
              // of it for a possible series of equal keys.
              haveSaveKey = true;
  
              /*
               * Multi-Key specific save key and lookup.
               */
  
              temp = saveKeyOutput;
              saveKeyOutput = currentKeyOutput;
              currentKeyOutput = temp;
  
              /*
               * Multi-key specific lookup key.
               */
  
              byte[] keyBytes = saveKeyOutput.getData();
              int keyLength = saveKeyOutput.getLength();
              saveJoinResult = hashSet.contains(keyBytes, 0, keyLength, hashSetResults[hashSetResultCount]);
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