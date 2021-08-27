//,temp,sample_2523.java,2,7,temp,sample_2520.java,2,7
//,3
public class xxx {
public RowSet fetchResults(OperationHandle opHandle, FetchOrientation orientation, long maxRows, FetchType fetchType) throws HiveSQLException {
RowSet rowSet = sessionManager.getOperationManager().getOperation(opHandle) .getParentSession().fetchResults(opHandle, orientation, maxRows, fetchType);


log.info("fetchresults");
}

};