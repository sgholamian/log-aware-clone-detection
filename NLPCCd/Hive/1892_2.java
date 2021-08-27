//,temp,sample_2507.java,2,9,temp,sample_2504.java,2,9
//,3
public class xxx {
public OperationHandle executeStatement(SessionHandle sessionHandle, String statement, Map<String, String> confOverlay) throws HiveSQLException {
HiveSession session = sessionManager.getSession(sessionHandle);
session.getSessionState().updateProgressMonitor(null);
OperationHandle opHandle = session.executeStatement(statement, confOverlay);


log.info("executestatement");
}

};