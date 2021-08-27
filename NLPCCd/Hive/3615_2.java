//,temp,sample_2501.java,2,7,temp,sample_2498.java,2,7
//,3
public class xxx {
public SessionHandle openSessionWithImpersonation(TProtocolVersion protocol, String username, String password, String ipAddress, Map<String, String> configuration, String delegationToken) throws HiveSQLException {
SessionHandle sessionHandle = sessionManager.openSession(protocol, username, password, ipAddress, configuration, true, delegationToken);


log.info("opensession");
}

};