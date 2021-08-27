//,temp,CLIService.java,207,213,temp,CLIService.java,188,193
//,3
public class xxx {
  public SessionHandle openSession(TProtocolVersion protocol, String username, String password, String ipAddress,
      Map<String, String> configuration) throws HiveSQLException {
    SessionHandle sessionHandle = sessionManager.openSession(protocol, username, password, ipAddress, configuration, false, null);
    LOG.debug(sessionHandle + ": openSession()");
    return sessionHandle;
  }

};