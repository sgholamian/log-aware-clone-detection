//,temp,HiveMetaStoreClient.java,3068,3086,temp,HiveMetaStoreClient.java,3020,3038
//,2
public class xxx {
  @Override
  public List<SQLNotNullConstraint> getNotNullConstraints(NotNullConstraintsRequest req)
      throws MetaException, NoSuchObjectException, TException {
    long t1 = System.currentTimeMillis();

    try {
      if (!req.isSetCatName()) {
        req.setCatName(getDefaultCatalog(conf));
      }

      return getNotNullConstraintsInternal(req).getNotNullConstraints();
    } finally {
      long diff = System.currentTimeMillis() - t1;
      if (LOG.isDebugEnabled()) {
        LOG.debug("class={}, method={}, duration={}, comments={}", CLASS_NAME, "getNotNullConstraints",
            diff, "HMS client");
      }
    }
  }

};