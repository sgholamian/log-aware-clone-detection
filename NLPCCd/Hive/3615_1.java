//,temp,sample_2501.java,2,7,temp,sample_2498.java,2,7
//,3
public class xxx {
public SessionHandle openSessionWithImpersonation(String username, String password, Map<String, String> configuration, String delegationToken) throws HiveSQLException {
SessionHandle sessionHandle = sessionManager.openSession(SERVER_VERSION, username, password, null, configuration, true, delegationToken);


log.info("opensession");
}

};