//,temp,LeveldbRMStateStore.java,581,593,temp,NMLeveldbStateStoreService.java,508,525
//,3
public class xxx {
  @Override
  public void storeContainerLaunched(ContainerId containerId)
      throws IOException {
    if (LOG.isDebugEnabled()) {
      LOG.debug("storeContainerLaunched: containerId=" + containerId);
    }

    // Removing the container if queued for backward compatibility reasons
    removeContainerQueued(containerId);
    String key = CONTAINERS_KEY_PREFIX + containerId.toString()
        + CONTAINER_LAUNCHED_KEY_SUFFIX;
    try {
      db.put(bytes(key), EMPTY_VALUE);
    } catch (DBException e) {
      markStoreUnHealthy(e);
      throw new IOException(e);
    }
  }

};