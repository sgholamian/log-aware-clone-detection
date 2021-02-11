//,temp,TokenAspect.java,155,164,temp,WebHdfsFileSystem.java,250,262
//,3
public class xxx {
  synchronized void initDelegationToken(UserGroupInformation ugi) {
    Token<?> token = selectDelegationToken(ugi);
    if (token != null) {
      if (LOG.isDebugEnabled()) {
        LOG.debug("Found existing DT for {}", token.getService());
      }
      fs.setDelegationToken(token);
      hasInitedToken = true;
    }
  }

};