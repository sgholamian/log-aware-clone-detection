//,temp,NMLeveldbStateStoreService.java,574,589,temp,NMLeveldbStateStoreService.java,508,525
//,3
public class xxx {
  @Override
  public void storeContainerCompleted(ContainerId containerId,
      int exitCode) throws IOException {
    if (LOG.isDebugEnabled()) {
      LOG.debug("storeContainerCompleted: containerId=" + containerId);
    }

    String key = CONTAINERS_KEY_PREFIX + containerId.toString()
        + CONTAINER_EXIT_CODE_KEY_SUFFIX;
    try {
      db.put(bytes(key), bytes(Integer.toString(exitCode)));
    } catch (DBException e) {
      markStoreUnHealthy(e);
      throw new IOException(e);
    }
  }

};