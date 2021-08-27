//,temp,CLIService.java,229,236,temp,CLIService.java,195,202
//,3
public class xxx {
  public SessionHandle openSessionWithImpersonation(TProtocolVersion protocol, String username,
      String password, String ipAddress, Map<String, String> configuration, String delegationToken)
          throws HiveSQLException {
    SessionHandle sessionHandle = sessionManager.openSession(protocol, username, password, ipAddress, configuration,
        true, delegationToken);
    LOG.debug(sessionHandle + ": openSession()");
    return sessionHandle;
  }

};