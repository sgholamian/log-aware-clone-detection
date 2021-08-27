//,temp,CLIService.java,411,419,temp,CLIService.java,385,393
//,3
public class xxx {
  @Override
  public OperationHandle getPrimaryKeys(SessionHandle sessionHandle,
      String catalog, String schema, String table)
          throws HiveSQLException {
    OperationHandle opHandle = sessionManager.getSession(sessionHandle)
        .getPrimaryKeys(catalog, schema, table);
    LOG.debug(sessionHandle + ": getPrimaryKeys()");
    return opHandle;
  }

};