//,temp,NMLeveldbStateStoreService.java,557,572,temp,NMLeveldbStateStoreService.java,490,506
//,3
public class xxx {
  @Override
  public void storeContainerKilled(ContainerId containerId)
      throws IOException {
    if (LOG.isDebugEnabled()) {
      LOG.debug("storeContainerKilled: containerId=" + containerId);
    }

    String key = CONTAINERS_KEY_PREFIX + containerId.toString()
        + CONTAINER_KILLED_KEY_SUFFIX;
    try {
      db.put(bytes(key), EMPTY_VALUE);
    } catch (DBException e) {
      markStoreUnHealthy(e);
      throw new IOException(e);
    }
  }

};