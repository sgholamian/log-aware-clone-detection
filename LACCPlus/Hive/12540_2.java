//,temp,TimestampColumnStatsMerger.java,37,67,temp,DecimalColumnStatsMerger.java,39,70
//,2
public class xxx {
  @Override
  public void merge(ColumnStatisticsObj aggregateColStats, ColumnStatisticsObj newColStats) {
    LOG.debug("Merging statistics: [aggregateColStats:{}, newColStats: {}]", aggregateColStats, newColStats);

    DecimalColumnStatsDataInspector aggregateData = decimalInspectorFromStats(aggregateColStats);
    DecimalColumnStatsDataInspector newData = decimalInspectorFromStats(newColStats);

    setLowValue(aggregateData, newData);
    setHighValue(aggregateData, newData);

    aggregateData.setNumNulls(aggregateData.getNumNulls() + newData.getNumNulls());

    if (aggregateData.getNdvEstimator() == null || newData.getNdvEstimator() == null) {
      aggregateData.setNumDVs(Math.max(aggregateData.getNumDVs(), newData.getNumDVs()));
    } else {
      NumDistinctValueEstimator oldEst = aggregateData.getNdvEstimator();
      NumDistinctValueEstimator newEst = newData.getNdvEstimator();
      final long ndv;
      if (oldEst.canMerge(newEst)) {
        oldEst.mergeEstimators(newEst);
        ndv = oldEst.estimateNumDistinctValues();
        aggregateData.setNdvEstimator(oldEst);
      } else {
        ndv = Math.max(aggregateData.getNumDVs(), newData.getNumDVs());
      }
      LOG.debug("Use bitvector to merge column {}'s ndvs of {} and {} to be {}", aggregateColStats.getColName(),
          aggregateData.getNumDVs(), newData.getNumDVs(), ndv);
      aggregateData.setNumDVs(ndv);
    }

    aggregateColStats.getStatsData().setDecimalStats(aggregateData);
  }

};