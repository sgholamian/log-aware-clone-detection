//,temp,sample_2499.java,2,7,temp,sample_2495.java,2,7
//,3
public class xxx {
public SessionHandle openSession(String username, String password, Map<String, String> configuration) throws HiveSQLException {
SessionHandle sessionHandle = sessionManager.openSession(SERVER_VERSION, username, password, null, configuration, false, null);


log.info("opensession");
}

};