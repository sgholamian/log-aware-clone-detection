//,temp,sample_2503.java,2,7,temp,sample_2510.java,2,7
//,3
public class xxx {
public OperationHandle getTables(SessionHandle sessionHandle, String catalogName, String schemaName, String tableName, List<String> tableTypes) throws HiveSQLException {
OperationHandle opHandle = sessionManager.getSession(sessionHandle) .getTables(catalogName, schemaName, tableName, tableTypes);


log.info("gettables");
}

};