//,temp,HiveMetaStoreClient.java,3328,3342,temp,HiveMetaStoreClient.java,3117,3134
//,3
public class xxx {
  @Override
  public SQLAllTableConstraints getAllTableConstraints(AllTableConstraintsRequest req)
      throws MetaException, TException {
    long t1 = 0;

    if (LOG.isDebugEnabled()) {
      t1 = System.currentTimeMillis();
    }

    try {
      return client.get_all_table_constraints(req).getAllTableConstraints();
    } finally {
      if (LOG.isDebugEnabled()) {
        LOG.debug("class={}, method={}, duration={}, comments={}", CLASS_NAME, "getAllTableConstraints",
            System.currentTimeMillis() - t1, "HMS client");
      }
    }
  }

};