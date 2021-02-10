//,temp,NMLeveldbStateStoreService.java,508,525,temp,NMLeveldbStateStoreService.java,473,488
//,3
public class xxx {
  @Override
  public void removeContainerPaused(ContainerId containerId)
      throws IOException {
    if (LOG.isDebugEnabled()) {
      LOG.debug("removeContainerPaused: containerId=" + containerId);
    }

    String key = CONTAINERS_KEY_PREFIX + containerId.toString()
        + CONTAINER_PAUSED_KEY_SUFFIX;
    try {
      db.delete(bytes(key));
    } catch (DBException e) {
      markStoreUnHealthy(e);
      throw new IOException(e);
    }
  }

};