//,temp,AMRMTokenSelector.java,40,56,temp,RMDelegationTokenSelector.java,48,64
//,2
public class xxx {
  @SuppressWarnings("unchecked")
  public Token<AMRMTokenIdentifier> selectToken(Text service,
      Collection<Token<? extends TokenIdentifier>> tokens) {
    if (service == null) {
      return null;
    }
    LOG.debug("Looking for a token with service " + service.toString());
    for (Token<? extends TokenIdentifier> token : tokens) {
      LOG.debug("Token kind is " + token.getKind().toString()
          + " and the token's service name is " + token.getService());
      if (AMRMTokenIdentifier.KIND_NAME.equals(token.getKind())
          && checkService(service, token)) {
        return (Token<AMRMTokenIdentifier>) token;
      }
    }
    return null;
  }

};