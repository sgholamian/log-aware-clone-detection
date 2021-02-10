//,temp,SwiftNativeInputStream.java,217,229,temp,WebHdfsFileSystem.java,1217,1230
//,3
public class xxx {
  @Override
  public synchronized void close() throws IOException {
    try {
      if (canRefreshDelegationToken && delegationToken != null) {
        cancelDelegationToken(delegationToken);
      }
    } catch (IOException ioe) {
      if (LOG.isDebugEnabled()) {
        LOG.debug("Token cancel failed: ", ioe);
      }
    } finally {
      super.close();
    }
  }

};