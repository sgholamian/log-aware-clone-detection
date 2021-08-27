//,temp,sample_2512.java,2,7,temp,sample_2515.java,2,7
//,3
public class xxx {
public OperationHandle getColumns(SessionHandle sessionHandle, String catalogName, String schemaName, String tableName, String columnName) throws HiveSQLException {
OperationHandle opHandle = sessionManager.getSession(sessionHandle) .getColumns(catalogName, schemaName, tableName, columnName);


log.info("getcolumns");
}

};