//,temp,CreateFunctionOperation.java,127,139,temp,DropFunctionOperation.java,94,106
//,2
public class xxx {
  private boolean skipIfNewerThenUpdate(String dbName, String functionName) throws HiveException {
    if (desc.getReplicationSpec().isInReplicationScope()) {
      Map<String, String> dbProps = Hive.get().getDatabase(dbName).getParameters();
      if (!desc.getReplicationSpec().allowEventReplacementInto(dbProps)) {
        // If the database is newer than the create event, then noop it.
        LOG.debug("FunctionTask: Create Function {} is skipped as database {} is newer than update",
            functionName, dbName);
        return true;
      }
    }

    return false;
  }

};