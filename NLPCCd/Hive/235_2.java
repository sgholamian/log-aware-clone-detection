//,temp,sample_2496.java,2,7,temp,sample_2497.java,2,7
//,2
public class xxx {
public SessionHandle openSession(TProtocolVersion protocol, String username, String password, String ipAddress, Map<String, String> configuration) throws HiveSQLException {
SessionHandle sessionHandle = sessionManager.openSession(protocol, username, password, ipAddress, configuration, false, null);


log.info("opensession");
}

};