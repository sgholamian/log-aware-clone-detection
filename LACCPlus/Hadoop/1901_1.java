//,temp,TimelineDelegationTokenSelector.java,40,60,temp,ClientToAMTokenSelector.java,36,52
//,3
public class xxx {
  @SuppressWarnings("unchecked")
  public Token<TimelineDelegationTokenIdentifier> selectToken(Text service,
      Collection<Token<? extends TokenIdentifier>> tokens) {
    if (service == null) {
      return null;
    }
    if (LOG.isDebugEnabled()) {
      LOG.debug("Looking for a token with service " + service.toString());
    }
    for (Token<? extends TokenIdentifier> token : tokens) {
      if (LOG.isDebugEnabled()) {
        LOG.debug("Token kind is " + token.getKind().toString()
            + " and the token's service name is " + token.getService());
      }
      if (TimelineDelegationTokenIdentifier.KIND_NAME.equals(token.getKind())
          && service.equals(token.getService())) {
        return (Token<TimelineDelegationTokenIdentifier>) token;
      }
    }
    return null;
  }

};