//,temp,Hive.java,3677,3707,temp,Hive.java,3296,3314
//,3
public class xxx {
  public List<String> getPartitionNames(String dbName, String tblName,
      Map<String, String> partSpec, short max) throws HiveException {
    List<String> names = null;
    Table t = getTable(dbName, tblName);

    List<String> pvals = MetaStoreUtils.getPvals(t.getPartCols(), partSpec);

    try {
      GetPartitionNamesPsRequest req = new GetPartitionNamesPsRequest();
      req.setTblName(tblName);
      req.setDbName(dbName);
      req.setPartValues(pvals);
      req.setMaxParts(max);
      if (AcidUtils.isTransactionalTable(t)) {
        ValidWriteIdList validWriteIdList = getValidWriteIdList(dbName, tblName);
        req.setValidWriteIdList(validWriteIdList != null ? validWriteIdList.toString() : null);
        req.setId(t.getTTable().getId());
      }
      GetPartitionNamesPsResponse res = getMSC().listPartitionNamesRequest(req);
      names = res.getNames();
    } catch (NoSuchObjectException nsoe) {
      // this means no partition exists for the given partition spec
      // key value pairs - thrift cannot handle null return values, hence
      // listPartitionNames() throws NoSuchObjectException to indicate null partitions
      return Lists.newArrayList();
    } catch (Exception e) {
      LOG.error("Failed getPartitionNames", e);
      throw new HiveException(e);
    }
    return names;
  }

};