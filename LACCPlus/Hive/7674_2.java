//,temp,CLIService.java,360,368,temp,CLIService.java,347,355
//,3
public class xxx {
  @Override
  public OperationHandle getSchemas(SessionHandle sessionHandle,
      String catalogName, String schemaName)
          throws HiveSQLException {
    OperationHandle opHandle = sessionManager.getSession(sessionHandle)
        .getSchemas(catalogName, schemaName);
    LOG.debug(sessionHandle + ": getSchemas()");
    return opHandle;
  }

};