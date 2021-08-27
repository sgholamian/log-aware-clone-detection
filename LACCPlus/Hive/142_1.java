//,temp,HMSHandler.java,10496,10511,temp,HMSHandler.java,10385,10399
//,3
public class xxx {
  @Override
  public void create_stored_procedure(StoredProcedure proc) throws NoSuchObjectException, MetaException {
    startFunction("create_stored_procedure");
    Exception ex = null;

    throwUnsupportedExceptionIfRemoteDB(proc.getDbName(), "create_stored_procedure");
    try {
      getMS().createOrUpdateStoredProcedure(proc);
    } catch (Exception e) {
      LOG.error("Caught exception", e);
      ex = e;
      throw e;
    } finally {
      endFunction("create_stored_procedure", ex == null, ex);
    }
  }

};