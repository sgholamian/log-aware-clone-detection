//,temp,sample_2514.java,2,7,temp,sample_2509.java,2,7
//,3
public class xxx {
public OperationHandle getPrimaryKeys(SessionHandle sessionHandle, String catalog, String schema, String table) throws HiveSQLException {
OperationHandle opHandle = sessionManager.getSession(sessionHandle) .getPrimaryKeys(catalog, schema, table);


log.info("getprimarykeys");
}

};