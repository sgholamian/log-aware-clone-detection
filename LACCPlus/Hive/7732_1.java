//,temp,HiveMetaStoreClient.java,3225,3247,temp,HiveMetaStoreClient.java,3192,3212
//,3
public class xxx {
  @Override
  public List<ColumnStatisticsObj> getTableColumnStatistics(String catName, String dbName,
      String tableName, List<String> colNames, String engine, String validWriteIdList) throws TException {
    long t1 = System.currentTimeMillis();

    try {
      if (colNames.isEmpty()) {
        return Collections.emptyList();
      }
      TableStatsRequest rqst = new TableStatsRequest(dbName, tableName, colNames, engine);
      rqst.setEngine(engine);
      rqst.setCatName(catName);
      rqst.setValidWriteIdList(validWriteIdList);

      return getTableColumnStatisticsInternal(rqst).getTableStats();
    } finally {
      long diff = System.currentTimeMillis() - t1;
      if (LOG.isDebugEnabled()) {
        LOG.debug("class={}, method={}, duration={}, comments={}", CLASS_NAME, "getTableColumnStatistics",
            diff, "HMS client");
      }
    }
  }

};