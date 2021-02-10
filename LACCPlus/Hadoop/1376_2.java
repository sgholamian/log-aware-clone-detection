//,temp,ClientHSTokenSelector.java,37,55,temp,ClientToAMTokenSelector.java,36,52
//,3
public class xxx {
  @SuppressWarnings("unchecked")
  public Token<ClientToAMTokenIdentifier> selectToken(Text service,
      Collection<Token<? extends TokenIdentifier>> tokens) {
    if (service == null) {
      return null;
    }
    LOG.debug("Looking for a token with service " + service.toString());
    for (Token<? extends TokenIdentifier> token : tokens) {
      LOG.debug("Token kind is " + token.getKind().toString()
          + " and the token's service name is " + token.getService());
      if (ClientToAMTokenIdentifier.KIND_NAME.equals(token.getKind())
          && service.equals(token.getService())) {
        return (Token<ClientToAMTokenIdentifier>) token;
      }
    }
    return null;
  }

};