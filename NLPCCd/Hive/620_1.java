//,temp,sample_2522.java,2,7,temp,sample_2521.java,2,7
//,3
public class xxx {
public TableSchema getResultSetMetadata(OperationHandle opHandle) throws HiveSQLException {
TableSchema tableSchema = sessionManager.getOperationManager() .getOperation(opHandle).getParentSession().getResultSetMetadata(opHandle);


log.info("getresultsetmetadata");
}

};