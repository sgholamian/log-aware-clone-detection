//,temp,ObjectStore.java,10322,10343,temp,ObjectStore.java,5212,5247
//,3
public class xxx {
  @Override
  public boolean removeMasterKey(Integer id) {
    LOG.debug("Begin executing removeMasterKey");
    boolean success = false;
    Query query = null;
    MMasterKey masterKey;
    try {
      openTransaction();
      query = pm.newQuery(MMasterKey.class, "keyId == id");
      query.declareParameters("java.lang.Integer id");
      query.setUnique(true);
      masterKey = (MMasterKey) query.execute(id);
      if (null != masterKey) {
        pm.deletePersistent(masterKey);
      }
      success = commitTransaction();
    } finally {
      rollbackAndCleanup(success, query);
    }
    LOG.debug("Done executing removeMasterKey with status : {}", success);
    return (null != masterKey) && success;
  }

};