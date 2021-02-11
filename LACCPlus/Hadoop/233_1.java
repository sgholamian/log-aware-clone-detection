//,temp,NMTokenSelector.java,36,54,temp,ClientHSTokenSelector.java,37,55
//,3
public class xxx {
  @SuppressWarnings("unchecked")
  @Override
  public Token<NMTokenIdentifier> selectToken(Text service,
      Collection<Token<? extends TokenIdentifier>> tokens) {
    if (service == null) {
      return null;
    }
    for (Token<? extends TokenIdentifier> token : tokens) {
      if (LOG.isDebugEnabled()) {
        LOG.info("Looking for service: " + service + ". Current token is "
            + token);
      }
      if (NMTokenIdentifier.KIND.equals(token.getKind()) && 
          service.equals(token.getService())) {
        return (Token<NMTokenIdentifier>) token;
      }
    }
    return null;
  }

};