//,temp,TezSessionState.java,736,743,temp,TezSessionState.java,727,734
//,3
public class xxx {
  protected final void cleanupDagResources() throws IOException {
    LOG.info("Attempting to clean up resources for {} : {}", sessionId, resources);
    if (resources != null) {
      FileSystem fs = resources.dagResourcesDir.getFileSystem(conf);
      fs.delete(resources.dagResourcesDir, true);
      resources = null;
    }
  }

};