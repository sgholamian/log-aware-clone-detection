//,temp,sample_2513.java,2,7,temp,sample_2524.java,2,7
//,2
public class xxx {
public OperationHandle getFunctions(SessionHandle sessionHandle, String catalogName, String schemaName, String functionName) throws HiveSQLException {
OperationHandle opHandle = sessionManager.getSession(sessionHandle) .getFunctions(catalogName, schemaName, functionName);


log.info("getfunctions");
}

};