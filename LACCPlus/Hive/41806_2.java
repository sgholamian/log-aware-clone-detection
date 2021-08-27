//,temp,ObjectStore.java,1151,1171,temp,ObjectStore.java,942,967
//,3
public class xxx {
  @Override
  public boolean dropDatabase(String catName, String dbname)
      throws NoSuchObjectException, MetaException {
    boolean success = false;
    LOG.info("Dropping database {}.{} along with all tables", catName, dbname);
    dbname = normalizeIdentifier(dbname);
    catName = normalizeIdentifier(catName);
    try {
      openTransaction();

      // then drop the database
      MDatabase db = getMDatabase(catName, dbname);
      pm.retrieve(db);
      List<MDBPrivilege> dbGrants = this.listDatabaseGrants(catName, dbname, null);
      if (CollectionUtils.isNotEmpty(dbGrants)) {
        pm.deletePersistentAll(dbGrants);
      }
      pm.deletePersistent(db);
      success = commitTransaction();
    } catch (Exception e) {
      throw new MetaException(e.getMessage() + " " + org.apache.hadoop.hive.metastore.utils.StringUtils.stringifyException(e));
    } finally {
      rollbackAndCleanup(success, null);
    }
    return success;
  }

};