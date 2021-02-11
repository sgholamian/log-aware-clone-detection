//,temp,SecurityUtil.java,422,432,temp,CGroupsHandlerImpl.java,482,496
//,3
public class xxx {
  public static void setTokenService(Token<?> token, InetSocketAddress addr) {
    Text service = buildTokenService(addr);
    if (token != null) {
      token.setService(service);
      if (LOG.isDebugEnabled()) {
        LOG.debug("Acquired token "+token);  // Token#toString() prints service
      }
    } else {
      LOG.warn("Failed to get token for service "+service);
    }
  }

};