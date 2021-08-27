//,temp,CLIService.java,616,621,temp,CLIService.java,608,614
//,2
public class xxx {
  @Override
  public void cancelDelegationToken(SessionHandle sessionHandle, HiveAuthFactory authFactory,
      String tokenStr) throws HiveSQLException {
    sessionManager.getSession(sessionHandle).
    cancelDelegationToken(authFactory, tokenStr);
    LOG.info(sessionHandle  + ": cancelDelegationToken()");
  }

};