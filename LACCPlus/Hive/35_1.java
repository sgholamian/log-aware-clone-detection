//,temp,CLIService.java,373,380,temp,CLIService.java,251,258
//,3
public class xxx {
  @Override
  public OperationHandle getTableTypes(SessionHandle sessionHandle)
      throws HiveSQLException {
    OperationHandle opHandle = sessionManager.getSession(sessionHandle)
        .getTableTypes();
    LOG.debug(sessionHandle + ": getTableTypes()");
    return opHandle;
  }

};