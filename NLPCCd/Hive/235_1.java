//,temp,sample_2496.java,2,7,temp,sample_2497.java,2,7
//,2
public class xxx {
public SessionHandle openSessionWithImpersonation(TProtocolVersion protocol, String username, String password, Map<String, String> configuration, String delegationToken) throws HiveSQLException {
SessionHandle sessionHandle = sessionManager.openSession(protocol, username, password, null, configuration, true, delegationToken);


log.info("opensessionwithimpersonation");
}

};