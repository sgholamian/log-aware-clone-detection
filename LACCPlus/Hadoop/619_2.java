//,temp,TokenAspect.java,134,149,temp,WebHdfsFileSystem.java,250,262
//,3
public class xxx {
  @VisibleForTesting
  synchronized boolean replaceExpiredDelegationToken() throws IOException {
    boolean replaced = false;
    if (canRefreshDelegationToken) {
      Token<?> token = getDelegationToken(null);
      if(LOG.isDebugEnabled()) {
        LOG.debug("Replaced expired token: {}", token);
      }
      setDelegationToken(token);
      replaced = (token != null);
    }
    return replaced;
  }

};