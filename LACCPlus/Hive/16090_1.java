//,temp,VectorMapJoinOuterMultiKeyOperator.java,136,435,temp,VectorMapJoinAntiJoinMultiKeyOperator.java,133,391
//,3
public class xxx {
  @Override
  public void processBatch(VectorizedRowBatch batch) throws HiveException {

    try {

      final int inputLogicalSize = batch.size;

      // Do the per-batch setup for an outer join.

      outerPerBatchSetup(batch);

      // For outer join, remember our input rows before ON expression filtering or before
      // hash table matching so we can generate results for all rows (matching and non matching)
      // later.
      boolean inputSelectedInUse = batch.selectedInUse;
      if (inputSelectedInUse) {
        System.arraycopy(batch.selected, 0, inputSelected, 0, inputLogicalSize);
      }

      // Filtering for outer join just removes rows available for hash table matching.
      boolean someRowsFilteredOut =  false;
      if (bigTableFilterExpressions.length > 0) {
        // Since the input
        for (VectorExpression ve : bigTableFilterExpressions) {
          ve.evaluate(batch);
        }
        someRowsFilteredOut = (batch.size != inputLogicalSize);
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
      // Also determine if any nulls are present since for a join that means no match.
      boolean allKeyInputColumnsRepeating;
      boolean someKeyInputColumnIsNull = false;  // Only valid if allKeyInputColumnsRepeating is true.
      if (bigTableKeyColumnMap.length == 0) {
       allKeyInputColumnsRepeating = false;
      } else {
        allKeyInputColumnsRepeating = true;
        for (int i = 0; i < bigTableKeyColumnMap.length; i++) {
          ColumnVector colVector = batch.cols[bigTableKeyColumnMap[i]];
          if (!colVector.isRepeating) {
            allKeyInputColumnsRepeating =  false;
            break;
          }
          if (!colVector.noNulls && colVector.isNull[0]) {
            someKeyInputColumnIsNull = true;
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

        JoinUtil.JoinResult joinResult;
        if (batch.size == 0) {
          // Whole repeated key batch was filtered out.
          joinResult = JoinUtil.JoinResult.NOMATCH;
        } else if (someKeyInputColumnIsNull) {
          // Any (repeated) null key column is no match for whole batch.
          joinResult = JoinUtil.JoinResult.NOMATCH;
        } else {

          // All key input columns are repeating.  Generate key once.  Lookup once.
          keyVectorSerializeWrite.setOutput(currentKeyOutput);
          keyVectorSerializeWrite.serializeWrite(batch, 0);
          byte[] keyBytes = currentKeyOutput.getData();
          int keyLength = currentKeyOutput.getLength();
          joinResult = hashMap.lookup(keyBytes, 0, keyLength, hashMapResults[0], matchTracker);
        }

        /*
         * Common repeated join result processing.
         */

        finishOuterRepeated(batch, joinResult, hashMapResults[0], someRowsFilteredOut,
            inputSelectedInUse, inputLogicalSize);
      } else {

        /*
         * NOT Repeating.
         */

        int selected[] = batch.selected;
        boolean selectedInUse = batch.selectedInUse;

        int hashMapResultCount = 0;
        int allMatchCount = 0;
        int equalKeySeriesCount = 0;
        int spillCount = 0;

        boolean atLeastOneNonMatch = someRowsFilteredOut;

        /*
         * Multi-Key specific variables.
         */

        Output temp;

        // We optimize performance by only looking up the first key in a series of equal keys.
        boolean haveSaveKey = false;
        JoinUtil.JoinResult saveJoinResult = JoinUtil.JoinResult.NOMATCH;

        // Logical loop over the rows in the batch since the batch may have selected in use.
        for (int logical = 0; logical < batch.size; logical++) {
          int batchIndex = (selectedInUse ? selected[logical] : logical);

          /*
           * Multi-Key outer null detection.
           */

          // Generate binary sortable key for current row in vectorized row batch.
          keyVectorSerializeWrite.setOutput(currentKeyOutput);
          keyVectorSerializeWrite.serializeWrite(batch, batchIndex);
          if (keyVectorSerializeWrite.getHasAnyNulls()) {

            // Have that the NULL does not interfere with the current equal key series, if there
            // is one. We do not set saveJoinResult.
            //
            //    Let a current MATCH equal key series keep going, or
            //    Let a current SPILL equal key series keep going, or
            //    Let a current NOMATCH keep not matching.

            atLeastOneNonMatch = true;

          } else {

            /*
             * Multi-Key outer get key.
             */

            // Generated earlier to get possible null(s).

            /*
             * Equal key series checking.
             */

            if (!haveSaveKey || !saveKeyOutput.arraysEquals(currentKeyOutput)) {

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

              // Regardless of our matching result, we keep that information to make multiple use
              // of it for a possible series of equal keys.
              haveSaveKey = true;

              /*
               * Multi-Key specific save key.
               */

              temp = saveKeyOutput;
              saveKeyOutput = currentKeyOutput;
              currentKeyOutput = temp;

              /*
               * Multi-Key specific lookup key.
               */

              byte[] keyBytes = saveKeyOutput.getData();
              int keyLength = saveKeyOutput.getLength();
              saveJoinResult = hashMap.lookup(keyBytes, 0, keyLength,
                  hashMapResults[hashMapResultCount], matchTracker);


              /*
               * Common outer join result processing.
               */

              switch (saveJoinResult) {
              case MATCH:
                equalKeySeriesHashMapResultIndices[equalKeySeriesCount] = hashMapResultCount;
                equalKeySeriesAllMatchIndices[equalKeySeriesCount] = allMatchCount;
                equalKeySeriesIsSingleValue[equalKeySeriesCount] = hashMapResults[hashMapResultCount].isSingleRow();
                equalKeySeriesDuplicateCounts[equalKeySeriesCount] = 1;
                allMatchs[allMatchCount++] = batchIndex;
                break;

              case SPILL:
                spills[spillCount] = batchIndex;
                spillHashMapResultIndices[spillCount] = hashMapResultCount;
                spillCount++;
                break;

              case NOMATCH:
                atLeastOneNonMatch = true;
                break;
              }
            } else {

              // Series of equal keys.

              switch (saveJoinResult) {
              case MATCH:
                equalKeySeriesDuplicateCounts[equalKeySeriesCount]++;
                allMatchs[allMatchCount++] = batchIndex;
                break;

              case SPILL:
                spills[spillCount] = batchIndex;
                spillHashMapResultIndices[spillCount] = hashMapResultCount;
                spillCount++;
                break;

              case NOMATCH:
                break;
              }
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
          LOG.debug(CLASS_NAME + " batch #" + batchCounter +
              " allMatchs " + intArrayToRangesString(allMatchs,allMatchCount) +
              " equalKeySeriesHashMapResultIndices " + intArrayToRangesString(equalKeySeriesHashMapResultIndices, equalKeySeriesCount) +
              " equalKeySeriesAllMatchIndices " + intArrayToRangesString(equalKeySeriesAllMatchIndices, equalKeySeriesCount) +
              " equalKeySeriesIsSingleValue " + Arrays.toString(Arrays.copyOfRange(equalKeySeriesIsSingleValue, 0, equalKeySeriesCount)) +
              " equalKeySeriesDuplicateCounts " + Arrays.toString(Arrays.copyOfRange(equalKeySeriesDuplicateCounts, 0, equalKeySeriesCount)) +
              " atLeastOneNonMatch " + atLeastOneNonMatch +
              " inputSelectedInUse " + inputSelectedInUse +
              " inputLogicalSize " + inputLogicalSize +
              " spills " + intArrayToRangesString(spills, spillCount) +
              " spillHashMapResultIndices " + intArrayToRangesString(spillHashMapResultIndices, spillCount) +
              " hashMapResults " + Arrays.toString(Arrays.copyOfRange(hashMapResults, 0, hashMapResultCount)));
        }

        // We will generate results for all matching and non-matching rows.
        finishOuter(batch,
            allMatchCount, equalKeySeriesCount, atLeastOneNonMatch,
            inputSelectedInUse, inputLogicalSize,
            spillCount, hashMapResultCount);
      }

      if (batch.size > 0) {

        // Forward any rows in the Big Table batch that had results added (they will be selected).
        // NOTE: Other result rows may have been generated in the overflowBatch.
        forwardBigTableBatch(batch);
      }

    } catch (IOException e) {
      throw new HiveException(e);
    } catch (Exception e) {
      throw new HiveException(e);
    }
  }

};