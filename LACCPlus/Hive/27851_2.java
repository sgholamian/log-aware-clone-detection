//,temp,Hive.java,5483,5504,temp,Hive.java,5437,5461
//,3
public class xxx {
  public List<ColumnStatisticsObj> getTableColumnStatistics(
      String dbName, String tableName, List<String> colNames, boolean checkTransactional)
      throws HiveException {

    PerfLogger perfLogger = SessionState.getPerfLogger();
    perfLogger.perfLogBegin(CLASS_NAME, PerfLogger.HIVE_GET_TABLE_COLUMN_STATS);
    List<ColumnStatisticsObj> retv = null;
    try {
      if (checkTransactional) {
        Table tbl = getTable(dbName, tableName);
        AcidUtils.TableSnapshot tableSnapshot = AcidUtils.getTableSnapshot(conf, tbl);
        retv = getMSC().getTableColumnStatistics(dbName, tableName, colNames, Constants.HIVE_ENGINE,
            tableSnapshot != null ? tableSnapshot.getValidWriteIdList() : null);
      } else {
        retv = getMSC().getTableColumnStatistics(dbName, tableName, colNames, Constants.HIVE_ENGINE);
      }

      return retv;
    } catch (Exception e) {
      LOG.debug("Failed getTableColumnStatistics", e);
      throw new HiveException(e);
    } finally {
      perfLogger.perfLogEnd(CLASS_NAME, PerfLogger.HIVE_GET_TABLE_COLUMN_STATS, "HS2-cache");
    }
  }

};