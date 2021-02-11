//,temp,TokenAspect.java,134,149,temp,WebHdfsFileSystem.java,250,262
//,3
public class xxx {
  synchronized void ensureTokenInitialized() throws IOException {
    // we haven't inited yet, or we used to have a token but it expired
    if (!hasInitedToken || (action != null && !action.isValid())) {
      //since we don't already have a token, go get one
      Token<?> token = fs.getDelegationToken(null);
      // security might be disabled
      if (token != null) {
        fs.setDelegationToken(token);
        addRenewAction(fs);
        if (LOG.isDebugEnabled()) {
          LOG.debug("Created new DT for {}", token.getService());
        }
      }
      hasInitedToken = true;
    }
  }

};