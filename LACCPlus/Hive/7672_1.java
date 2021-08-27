//,temp,CLIService.java,229,236,temp,CLIService.java,195,202
//,3
public class xxx {
  @Override
  public SessionHandle openSessionWithImpersonation(String username, String password, Map<String, String> configuration,
      String delegationToken) throws HiveSQLException {
    SessionHandle sessionHandle = sessionManager.openSession(SERVER_VERSION, username, password, null, configuration,
        true, delegationToken);
    LOG.debug(sessionHandle + ": openSession()");
    return sessionHandle;
  }

};