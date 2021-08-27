//,temp,CLIService.java,411,419,temp,CLIService.java,385,393
//,3
public class xxx {
  @Override
  public OperationHandle getColumns(SessionHandle sessionHandle,
      String catalogName, String schemaName, String tableName, String columnName)
          throws HiveSQLException {
    OperationHandle opHandle = sessionManager.getSession(sessionHandle)
        .getColumns(catalogName, schemaName, tableName, columnName);
    LOG.debug(sessionHandle + ": getColumns()");
    return opHandle;
  }

};