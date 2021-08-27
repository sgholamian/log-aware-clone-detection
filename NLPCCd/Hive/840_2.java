//,temp,sample_2506.java,2,9,temp,sample_2505.java,2,9
//,3
public class xxx {
public OperationHandle executeStatement(SessionHandle sessionHandle, String statement, Map<String, String> confOverlay, long queryTimeout) throws HiveSQLException {
HiveSession session = sessionManager.getSession(sessionHandle);
session.getSessionState().updateProgressMonitor(null);
OperationHandle opHandle = session.executeStatement(statement, confOverlay, queryTimeout);


log.info("executestatement");
}

};