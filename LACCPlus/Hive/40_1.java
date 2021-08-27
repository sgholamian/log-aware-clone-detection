//,temp,CLIService.java,398,406,temp,CLIService.java,323,330
//,3
public class xxx {
  @Override
  public OperationHandle getFunctions(SessionHandle sessionHandle,
      String catalogName, String schemaName, String functionName)
          throws HiveSQLException {
    OperationHandle opHandle = sessionManager.getSession(sessionHandle)
        .getFunctions(catalogName, schemaName, functionName);
    LOG.debug(sessionHandle + ": getFunctions()");
    return opHandle;
  }

};