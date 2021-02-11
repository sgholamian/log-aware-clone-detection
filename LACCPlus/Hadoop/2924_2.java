//,temp,LeveldbRMStateStore.java,601,614,temp,NMLeveldbStateStoreService.java,457,471
//,3
public class xxx {
  @Override
  public void storeContainerPaused(ContainerId containerId) throws IOException {
    if (LOG.isDebugEnabled()) {
      LOG.debug("storeContainerPaused: containerId=" + containerId);
    }

    String key = CONTAINERS_KEY_PREFIX + containerId.toString()
        + CONTAINER_PAUSED_KEY_SUFFIX;
    try {
      db.put(bytes(key), EMPTY_VALUE);
    } catch (DBException e) {
      markStoreUnHealthy(e);
      throw new IOException(e);
    }
  }

};