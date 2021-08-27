//,temp,CLIService.java,360,368,temp,CLIService.java,347,355
//,3
public class xxx {
  @Override
  public OperationHandle getTables(SessionHandle sessionHandle,
      String catalogName, String schemaName, String tableName, List<String> tableTypes)
          throws HiveSQLException {
    OperationHandle opHandle = sessionManager.getSession(sessionHandle)
        .getTables(catalogName, schemaName, tableName, tableTypes);
    LOG.debug(sessionHandle + ": getTables()");
    return opHandle;
  }

};