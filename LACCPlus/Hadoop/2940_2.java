//,temp,DFSClient.java,774,788,temp,DFSClient.java,732,744
//,3
public class xxx {
  @Deprecated
  public void cancelDelegationToken(Token<DelegationTokenIdentifier> token)
      throws IOException {
    LOG.info("Cancelling " + DelegationTokenIdentifier.stringifyToken(token));
    try {
      token.cancel(conf);
    } catch (InterruptedException ie) {
      throw new RuntimeException("caught interrupted", ie);
    } catch (RemoteException re) {
      throw re.unwrapRemoteException(InvalidToken.class,
          AccessControlException.class);
    }
  }

};