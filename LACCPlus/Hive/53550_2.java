//,temp,Hive.java,3677,3707,temp,Hive.java,3296,3314
//,3
public class xxx {
  public org.apache.hadoop.hive.metastore.api.Partition getPartition(Table t, String dbName, String tableName,
      List<String> params) throws HiveException {
    try {
      GetPartitionRequest req = new GetPartitionRequest();
      req.setDbName(dbName);
      req.setTblName(tableName);
      req.setPartVals(params);
      if (AcidUtils.isTransactionalTable(t)) {
        ValidWriteIdList validWriteIdList = getValidWriteIdList(dbName, tableName);
        req.setValidWriteIdList(validWriteIdList != null ? validWriteIdList.toString() : null);
        req.setId(t.getTTable().getId());
      }
      GetPartitionResponse res = getMSC().getPartitionRequest(req);
      return res.getPartition();
    } catch (Exception e) {
      LOG.error("Failed getPartition", e);
      throw new HiveException(e);
    }
  }

};