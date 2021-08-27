//,temp,HiveMetaStoreClient.java,1221,1248,temp,HiveMetaStoreClientPreCatalog.java,799,824
//,3
public class xxx {
  public void createTable(Table tbl, EnvironmentContext envContext) throws AlreadyExistsException,
      InvalidObjectException, MetaException, NoSuchObjectException, TException {
    if (!tbl.isSetCatName()) {
      tbl.setCatName(getDefaultCatalog(conf));
    }
    HiveMetaHook hook = getHook(tbl);
    if (hook != null) {
      hook.preCreateTable(tbl);
    }
    boolean success = false;
    try {
      // Subclasses can override this step (for example, for temporary tables)
      create_table_with_environment_context(tbl, envContext);
      if (hook != null) {
        hook.commitCreateTable(tbl);
      }
      success = true;
    }
    finally {
      if (!success && (hook != null)) {
        try {
          hook.rollbackCreateTable(tbl);
        } catch (Exception e){
          LOG.error("Create rollback failed with", e);
        }
      }
    }
  }

};