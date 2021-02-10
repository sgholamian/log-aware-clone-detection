//,temp,AbstractJavaKeyStoreProvider.java,161,167,temp,AbstractYarnScheduler.java,402,413
//,3
public class xxx {
  protected void initFileSystem(URI keystoreUri)
      throws IOException {
    path = ProviderUtils.unnestUri(keystoreUri);
    if (LOG.isDebugEnabled()) {
      LOG.debug("backing jks path initialized to " + path);
    }
  }

};