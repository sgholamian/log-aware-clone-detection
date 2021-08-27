//,temp,ObjectStore.java,1151,1171,temp,ObjectStore.java,942,967
//,3
public class xxx {
  @Override
  public boolean dropDataConnector(String dcname)
      throws NoSuchObjectException, MetaException {
    boolean success = false;
    LOG.info("Dropping dataconnector {} ", dcname);
    dcname = normalizeIdentifier(dcname);
    try {
      openTransaction();

      // then drop the dataconnector
      MDataConnector mdb = getMDataConnector(dcname);
      pm.retrieve(mdb);
      pm.deletePersistent(mdb);
      success = commitTransaction();
    } catch (Exception e) {
      throw new MetaException(e.getMessage() + " " + org.apache.hadoop.hive.metastore.utils.StringUtils.stringifyException(e));
    } finally {
      rollbackAndCleanup(success, null);
    }
    return success;
  }

};