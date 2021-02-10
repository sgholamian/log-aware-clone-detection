//,temp,TimelineDelegationTokenSelector.java,40,60,temp,ContainerTokenSelector.java,40,58
//,3
public class xxx {
  @SuppressWarnings("unchecked")
  @Override
  public Token<ContainerTokenIdentifier> selectToken(Text service,
      Collection<Token<? extends TokenIdentifier>> tokens) {
    if (service == null) {
      return null;
    }
    for (Token<? extends TokenIdentifier> token : tokens) {
      if (LOG.isDebugEnabled()) {
        LOG.info("Looking for service: " + service + ". Current token is "
            + token);
      }
      if (ContainerTokenIdentifier.KIND.equals(token.getKind()) && 
          service.equals(token.getService())) {
        return (Token<ContainerTokenIdentifier>) token;
      }
    }
    return null;
  }

};