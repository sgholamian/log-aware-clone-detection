//,temp,CLIService.java,207,213,temp,CLIService.java,188,193
//,3
public class xxx {
  @Override
  public SessionHandle openSession(String username, String password, Map<String, String> configuration)
      throws HiveSQLException {
    SessionHandle sessionHandle = sessionManager.openSession(SERVER_VERSION, username, password, null, configuration, false, null);
    LOG.debug(sessionHandle + ": openSession()");
    return sessionHandle;
  }

};