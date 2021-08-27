//,temp,CLIService.java,398,406,temp,CLIService.java,323,330
//,3
public class xxx {
  @Override
  public OperationHandle getTypeInfo(SessionHandle sessionHandle)
      throws HiveSQLException {
    OperationHandle opHandle = sessionManager.getSession(sessionHandle)
        .getTypeInfo();
    LOG.debug(sessionHandle + ": getTypeInfo()");
    return opHandle;
  }

};