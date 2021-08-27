//,temp,sample_2506.java,2,9,temp,sample_2505.java,2,9
//,3
public class xxx {
public OperationHandle executeStatementAsync(SessionHandle sessionHandle, String statement, Map<String, String> confOverlay) throws HiveSQLException {
HiveSession session = sessionManager.getSession(sessionHandle);
session.getSessionState().updateProgressMonitor(null);
OperationHandle opHandle = session.executeStatementAsync(statement, confOverlay);


log.info("executestatementasync");
}

};