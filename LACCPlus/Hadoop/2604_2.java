//,temp,IOUtils.java,319,327,temp,WebHdfsFileSystem.java,1511,1522
//,3
public class xxx {
  @Override
  public synchronized void close() throws IOException {
    try {
      if (canRefreshDelegationToken && delegationToken != null) {
        cancelDelegationToken(delegationToken);
      }
    } catch (IOException ioe) {
      LOG.debug("Token cancel failed: ", ioe);
    } finally {
      super.close();
    }
  }

};