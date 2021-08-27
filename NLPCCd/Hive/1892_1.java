//,temp,sample_2507.java,2,9,temp,sample_2504.java,2,9
//,3
public class xxx {
public OperationHandle executeStatementAsync(SessionHandle sessionHandle, String statement, Map<String, String> confOverlay, long queryTimeout) throws HiveSQLException {
HiveSession session = sessionManager.getSession(sessionHandle);
session.getSessionState().updateProgressMonitor(null);
OperationHandle opHandle = session.executeStatementAsync(statement, confOverlay, queryTimeout);


log.info("executestatementasync");
}

};