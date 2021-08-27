//,temp,Hive.java,5483,5504,temp,Hive.java,5437,5461
//,3
public class xxx {
  public AggrStats getAggrColStatsFor(String dbName, String tblName,
     List<String> colNames, List<String> partName, boolean checkTransactional) {
    PerfLogger perfLogger = SessionState.getPerfLogger();
    perfLogger.perfLogBegin(CLASS_NAME, PerfLogger.HIVE_GET_AGGR_COL_STATS);
    String writeIdList = null;
    try {
      if (checkTransactional) {
        Table tbl = getTable(dbName, tblName);
        AcidUtils.TableSnapshot tableSnapshot = AcidUtils.getTableSnapshot(conf, tbl);
        writeIdList = tableSnapshot != null ? tableSnapshot.getValidWriteIdList() : null;
      }
      AggrStats result = getMSC().getAggrColStatsFor(dbName, tblName, colNames, partName, Constants.HIVE_ENGINE,
              writeIdList);

      return result;
    } catch (Exception e) {
      LOG.debug("Failed getAggrColStatsFor", e);
      return new AggrStats(new ArrayList<ColumnStatisticsObj>(),0);
    } finally {
      perfLogger.perfLogEnd(CLASS_NAME, PerfLogger.HIVE_GET_AGGR_COL_STATS, "HS2-cache");
    }
  }

};