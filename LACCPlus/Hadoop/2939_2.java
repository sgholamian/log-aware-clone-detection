//,temp,DFSClient.java,732,744,temp,DFSClient.java,712,724
//,3
public class xxx {
  @Deprecated
  public long renewDelegationToken(Token<DelegationTokenIdentifier> token)
      throws IOException {
    LOG.info("Renewing " + DelegationTokenIdentifier.stringifyToken(token));
    try {
      return token.renew(conf);
    } catch (InterruptedException ie) {
      throw new RuntimeException("caught interrupted", ie);
    } catch (RemoteException re) {
      throw re.unwrapRemoteException(InvalidToken.class,
          AccessControlException.class);
    }
  }

};