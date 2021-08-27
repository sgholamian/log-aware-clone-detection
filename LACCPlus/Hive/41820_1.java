//,temp,HiveMetaStoreClientPreCatalog.java,2862,2874,temp,ObjectStore.java,9615,9625
//,3
public class xxx {
  @Override
  public AggrStats getAggrColStatsFor(
      String dbName, String tblName, List<String> colNames,
      List<String> partName, String engine, String writeIdList)
      throws NoSuchObjectException, MetaException, TException {
    if (colNames.isEmpty() || partName.isEmpty()) {
      LOG.debug("Columns is empty or partNames is empty : Short-circuiting stats eval on client side.");
      return new AggrStats(new ArrayList<>(),0); // Nothing to aggregate
    }
    PartitionsStatsRequest req = new PartitionsStatsRequest(dbName, tblName, colNames, partName, engine);
    req.setValidWriteIdList(writeIdList);
    return client.get_aggr_stats_for(req);
  }

};