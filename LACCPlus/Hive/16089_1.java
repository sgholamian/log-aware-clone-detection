//,temp,TimestampColumnStatsAggregator.java,49,240,temp,StringColumnStatsAggregator.java,47,206
//,3
public class xxx {
  @Override
  public ColumnStatisticsObj aggregate(List<ColStatsObjWithSourceInfo> colStatsWithSourceInfo,
                                       List<String> partNames, boolean areAllPartsFound) throws MetaException {
    ColumnStatisticsObj statsObj = null;
    String colType = null;
    String colName = null;
    // check if all the ColumnStatisticsObjs contain stats and all the ndv are
    // bitvectors
    boolean doAllPartitionContainStats = partNames.size() == colStatsWithSourceInfo.size();
    NumDistinctValueEstimator ndvEstimator = null;
    for (ColStatsObjWithSourceInfo csp : colStatsWithSourceInfo) {
      ColumnStatisticsObj cso = csp.getColStatsObj();
      if (statsObj == null) {
        colName = cso.getColName();
        colType = cso.getColType();
        statsObj = ColumnStatsAggregatorFactory.newColumnStaticsObj(colName, colType,
            cso.getStatsData().getSetField());
        LOG.trace("doAllPartitionContainStats for column: {} is: {}", colName, doAllPartitionContainStats);
      }
      TimestampColumnStatsDataInspector timestampColumnStats = timestampInspectorFromStats(cso);

      if (timestampColumnStats.getNdvEstimator() == null) {
        ndvEstimator = null;
        break;
      } else {
        // check if all of the bit vectors can merge
        NumDistinctValueEstimator estimator = timestampColumnStats.getNdvEstimator();
        if (ndvEstimator == null) {
          ndvEstimator = estimator;
        } else {
          if (ndvEstimator.canMerge(estimator)) {
            continue;
          } else {
            ndvEstimator = null;
            break;
          }
        }
      }
    }
    if (ndvEstimator != null) {
      ndvEstimator = NumDistinctValueEstimatorFactory
          .getEmptyNumDistinctValueEstimator(ndvEstimator);
    }
    LOG.debug("all of the bit vectors can merge for " + colName + " is " + (ndvEstimator != null));
    ColumnStatisticsData columnStatisticsData = new ColumnStatisticsData();
    if (doAllPartitionContainStats || colStatsWithSourceInfo.size() < 2) {
      TimestampColumnStatsDataInspector aggregateData = null;
      long lowerBound = 0;
      long higherBound = 0;
      double densityAvgSum = 0.0;
      for (ColStatsObjWithSourceInfo csp : colStatsWithSourceInfo) {
        ColumnStatisticsObj cso = csp.getColStatsObj();
        TimestampColumnStatsDataInspector newData = timestampInspectorFromStats(cso);
        higherBound += newData.getNumDVs();
        if (newData.isSetLowValue() && newData.isSetHighValue()) {
          densityAvgSum += (diff(newData.getHighValue(), newData.getLowValue())) / newData.getNumDVs();
        }
        if (ndvEstimator != null) {
          ndvEstimator.mergeEstimators(newData.getNdvEstimator());
        }
        if (aggregateData == null) {
          aggregateData = newData.deepCopy();
        } else {
          TimestampColumnStatsMerger merger = new TimestampColumnStatsMerger();
          merger.setLowValue(aggregateData, newData);
          merger.setHighValue(aggregateData, newData);

          aggregateData.setNumNulls(aggregateData.getNumNulls() + newData.getNumNulls());
          aggregateData.setNumDVs(Math.max(aggregateData.getNumDVs(), newData.getNumDVs()));
        }
      }
      if (ndvEstimator != null) {
        // if all the ColumnStatisticsObjs contain bitvectors, we do not need to
        // use uniform distribution assumption because we can merge bitvectors
        // to get a good estimation.
        aggregateData.setNumDVs(ndvEstimator.estimateNumDistinctValues());
      } else {
        long estimation;
        if (useDensityFunctionForNDVEstimation) {
          // We have estimation, lowerbound and higherbound. We use estimation
          // if it is between lowerbound and higherbound.
          double densityAvg = densityAvgSum / partNames.size();
          estimation = (long) (diff(aggregateData.getHighValue(), aggregateData.getLowValue()) / densityAvg);
          if (estimation < lowerBound) {
            estimation = lowerBound;
          } else if (estimation > higherBound) {
            estimation = higherBound;
          }
        } else {
          estimation = (long) (lowerBound + (higherBound - lowerBound) * ndvTuner);
        }
        aggregateData.setNumDVs(estimation);
      }
      columnStatisticsData.setTimestampStats(aggregateData);
    } else {
      // we need extrapolation
      LOG.debug("start extrapolation for " + colName);

      Map<String, Integer> indexMap = new HashMap<>();
      for (int index = 0; index < partNames.size(); index++) {
        indexMap.put(partNames.get(index), index);
      }
      Map<String, Double> adjustedIndexMap = new HashMap<>();
      Map<String, ColumnStatisticsData> adjustedStatsMap = new HashMap<>();
      // while we scan the css, we also get the densityAvg, lowerbound and
      // higerbound when useDensityFunctionForNDVEstimation is true.
      double densityAvgSum = 0.0;
      if (ndvEstimator == null) {
        // if not every partition uses bitvector for ndv, we just fall back to
        // the traditional extrapolation methods.
        for (ColStatsObjWithSourceInfo csp : colStatsWithSourceInfo) {
          ColumnStatisticsObj cso = csp.getColStatsObj();
          String partName = csp.getPartName();
          TimestampColumnStatsData newData = cso.getStatsData().getTimestampStats();
          if (useDensityFunctionForNDVEstimation) {
            densityAvgSum += diff(newData.getHighValue(), newData.getLowValue()) / newData.getNumDVs();
          }
          adjustedIndexMap.put(partName, (double) indexMap.get(partName));
          adjustedStatsMap.put(partName, cso.getStatsData());
        }
      } else {
        // we first merge all the adjacent bitvectors that we could merge and
        // derive new partition names and index.
        StringBuilder pseudoPartName = new StringBuilder();
        double pseudoIndexSum = 0;
        int length = 0;
        int curIndex = -1;
        TimestampColumnStatsDataInspector aggregateData = null;
        for (ColStatsObjWithSourceInfo csp : colStatsWithSourceInfo) {
          ColumnStatisticsObj cso = csp.getColStatsObj();
          String partName = csp.getPartName();
          TimestampColumnStatsDataInspector newData = timestampInspectorFromStats(cso);
          // newData.isSetBitVectors() should be true for sure because we
          // already checked it before.
          if (indexMap.get(partName) != curIndex) {
            // There is bitvector, but it is not adjacent to the previous ones.
            if (length > 0) {
              // we have to set ndv
              adjustedIndexMap.put(pseudoPartName.toString(), pseudoIndexSum / length);
              aggregateData.setNumDVs(ndvEstimator.estimateNumDistinctValues());
              ColumnStatisticsData csd = new ColumnStatisticsData();
              csd.setTimestampStats(aggregateData);
              adjustedStatsMap.put(pseudoPartName.toString(), csd);
              if (useDensityFunctionForNDVEstimation) {
                densityAvgSum += diff(aggregateData.getHighValue(), aggregateData.getLowValue())
                    / aggregateData.getNumDVs();
              }
              // reset everything
              pseudoPartName = new StringBuilder();
              pseudoIndexSum = 0;
              length = 0;
              ndvEstimator = NumDistinctValueEstimatorFactory.getEmptyNumDistinctValueEstimator(ndvEstimator);
            }
            aggregateData = null;
          }
          curIndex = indexMap.get(partName);
          pseudoPartName.append(partName);
          pseudoIndexSum += curIndex;
          length++;
          curIndex++;
          if (aggregateData == null) {
            aggregateData = newData.deepCopy();
          } else {
            aggregateData.setLowValue(min(aggregateData.getLowValue(), newData.getLowValue()));
            aggregateData.setHighValue(max(aggregateData.getHighValue(), newData.getHighValue()));
            aggregateData.setNumNulls(aggregateData.getNumNulls() + newData.getNumNulls());
          }
          ndvEstimator.mergeEstimators(newData.getNdvEstimator());
        }
        if (length > 0) {
          // we have to set ndv
          adjustedIndexMap.put(pseudoPartName.toString(), pseudoIndexSum / length);
          aggregateData.setNumDVs(ndvEstimator.estimateNumDistinctValues());
          ColumnStatisticsData csd = new ColumnStatisticsData();
          csd.setTimestampStats(aggregateData);
          adjustedStatsMap.put(pseudoPartName.toString(), csd);
          if (useDensityFunctionForNDVEstimation) {
            densityAvgSum += diff(aggregateData.getHighValue(), aggregateData.getLowValue())
                / aggregateData.getNumDVs();
          }
        }
      }
      extrapolate(columnStatisticsData, partNames.size(), colStatsWithSourceInfo.size(),
          adjustedIndexMap, adjustedStatsMap, densityAvgSum / adjustedStatsMap.size());
    }
    LOG.debug(
        "Ndv estimatation for {} is {} # of partitions requested: {} # of partitions found: {}",
        colName, columnStatisticsData.getTimestampStats().getNumDVs(), partNames.size(),
        colStatsWithSourceInfo.size());
    statsObj.setStatsData(columnStatisticsData);
    return statsObj;
  }

};