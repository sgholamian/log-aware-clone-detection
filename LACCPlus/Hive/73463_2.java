//,temp,UpgradeTool.java,614,635,temp,HiveMetaStoreClient.java,4343,4365
//,3
public class xxx {
  @Override
  public AggrStats getAggrColStatsFor(String catName, String dbName, String tblName,
      List<String> colNames, List<String> partNames, String engine) throws TException {
    long t1 = System.currentTimeMillis();

    try {
      if (colNames.isEmpty() || partNames.isEmpty()) {
        LOG.debug("Columns is empty or partNames is empty : Short-circuiting stats eval on client side.");
        return new AggrStats(new ArrayList<>(), 0); // Nothing to aggregate
      }
      PartitionsStatsRequest req = new PartitionsStatsRequest(dbName, tblName, colNames, partNames, engine);
      req.setCatName(catName);
      req.setValidWriteIdList(getValidWriteIdList(dbName, tblName));

      return getAggrStatsForInternal(req);
    } finally {
      long diff = System.currentTimeMillis() - t1;
      if (LOG.isDebugEnabled()) {
        LOG.debug("class={}, method={}, duration={}, comments={}", CLASS_NAME, "getAggrColStatsFor",
            diff, "HMS client");
      }
    }
  }

};