//,temp,sample_2514.java,2,7,temp,sample_2509.java,2,7
//,3
public class xxx {
public OperationHandle getSchemas(SessionHandle sessionHandle, String catalogName, String schemaName) throws HiveSQLException {
OperationHandle opHandle = sessionManager.getSession(sessionHandle) .getSchemas(catalogName, schemaName);


log.info("getschemas");
}

};