//,temp,VectorMapJoinInnerLongOperator.java,129,390,temp,VectorMapJoinOuterStringOperator.java,121,406
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
      boolean someRowsFilteredOut = false;
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
       * Single-Column String specific declarations.
       */

      // The one join column for this specialized class.
      BytesColumnVector joinColVector = (BytesColumnVector) batch.cols[singleJoinColumn];
      byte[][] vector = joinColVector.vector;
      int[] start = joinColVector.start;
      int[] length = joinColVector.length;

      /*
       * Single-Column String check for repeating.
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
        if (batch.size == 0) {
          // Whole repeated key batch was filtered out.
          joinResult = JoinUtil.JoinResult.NOMATCH;
        } else if (!joinColVector.noNulls && joinColVector.isNull[0]) {
          // Any (repeated) null key column is no match for whole batch.
          joinResult = JoinUtil.JoinResult.NOMATCH;
        } else {
          // Handle *repeated* join key, if found.
          byte[] keyBytes = vector[0];
          int keyStart = start[0];
          int keyLength = length[0];
          joinResult = hashMap.lookup(
              keyBytes, keyStart, keyLength, hashMapResults[0], matchTracker);
        }

        /*
         * Common repeated join result processing.
         */

        if (LOG.isDebugEnabled()) {
          LOG.debug(CLASS_NAME + " batch #" + batchCounter + " repeated joinResult " + joinResult.name());
        }
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
         * Single-Column String specific variables.
         */

        int saveKeyBatchIndex = -1;

        // We optimize performance by only looking up the first key in a series of equal keys.
        boolean haveSaveKey = false;
        JoinUtil.JoinResult saveJoinResult = JoinUtil.JoinResult.NOMATCH;

        // Logical loop over the rows in the batch since the batch may have selected in use.
        for (int logical = 0; logical < batch.size; logical++) {
          int batchIndex = (selectedInUse ? selected[logical] : logical);

          /*
           * Single-Column String outer null detection.
           */

          boolean isNull = !joinColVector.noNulls && joinColVector.isNull[batchIndex];

          if (isNull) {

            // Have that the NULL does not interfere with the current equal key series, if there
            // is one. We do not set saveJoinResult.
            //
            //    Let a current MATCH equal key series keep going, or
            //    Let a current SPILL equal key series keep going, or
            //    Let a current NOMATCH keep not matching.

            atLeastOneNonMatch = true;

          } else {

            /*
             * Single-Column String outer get key.
             */

            // Implicit -- use batchIndex.

            /*
             * Equal key series checking.
             */

            if (!haveSaveKey ||
                StringExpr.equal(vector[saveKeyBatchIndex], start[saveKeyBatchIndex], length[saveKeyBatchIndex],
                                   vector[batchIndex], start[batchIndex], length[batchIndex]) == false) {
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
               * Single-Column String specific save key.
               */

              saveKeyBatchIndex = batchIndex;

              /*
               * Single-Column Long specific lookup key.
               */

              byte[] keyBytes = vector[batchIndex];
              int keyStart = start[batchIndex];
              int keyLength = length[batchIndex];
              saveJoinResult = hashMap.lookup(keyBytes, keyStart, keyLength,
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