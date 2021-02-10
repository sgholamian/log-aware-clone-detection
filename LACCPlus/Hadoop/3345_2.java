//,temp,NMLeveldbStateStoreService.java,457,471,temp,NMLeveldbStateStoreService.java,425,439
//,2
public class xxx {
  @Override
  public void storeContainerQueued(ContainerId containerId) throws IOException {
    if (LOG.isDebugEnabled()) {
      LOG.debug("storeContainerQueued: containerId=" + containerId);
    }

    String key = CONTAINERS_KEY_PREFIX + containerId.toString()
        + CONTAINER_QUEUED_KEY_SUFFIX;
    try {
      db.put(bytes(key), EMPTY_VALUE);
    } catch (DBException e) {
      markStoreUnHealthy(e);
      throw new IOException(e);
    }
  }

};