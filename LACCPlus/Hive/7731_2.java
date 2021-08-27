//,temp,HiveMetaStoreClient.java,2028,2048,temp,HiveMetaStoreClient.java,1992,2010
//,3
public class xxx {
  @Override
  public List<Partition> listPartitionsWithAuthInfo(String catName, String dbName, String tableName,
                                                    int maxParts, String userName,
                                                    List<String> groupNames) throws TException {
    // TODO should we add capabilities here as well as it returns Partition objects
    long t1 = System.currentTimeMillis();
    try {
      List<Partition> parts = listPartitionsWithAuthInfoInternal(catName, dbName, tableName,
          maxParts, userName, groupNames);

      return deepCopyPartitions(FilterUtils.filterPartitionsIfEnabled(isClientFilterEnabled, filterHook, parts));
    } finally {
      long diff = System.currentTimeMillis() - t1;
      if (LOG.isDebugEnabled()) {
        LOG.debug("class={}, method={}, duration={}, comments={}", CLASS_NAME, "listPartitionsWithAuthInfo",
            diff, "HMS client");
      }
    }
  }

};