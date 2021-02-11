//,temp,DFSClient.java,774,788,temp,DFSClient.java,732,744
//,3
public class xxx {
    @SuppressWarnings("unchecked")
    @Override
    public void cancel(Token<?> token, Configuration conf) throws IOException {
      Token<DelegationTokenIdentifier> delToken =
          (Token<DelegationTokenIdentifier>) token;
      LOG.info("Cancelling " +
          DelegationTokenIdentifier.stringifyToken(delToken));
      ClientProtocol nn = getNNProxy(delToken, conf);
      try {
        nn.cancelDelegationToken(delToken);
      } catch (RemoteException re) {
        throw re.unwrapRemoteException(InvalidToken.class,
            AccessControlException.class);
      }
    }

};