//,temp,NMLeveldbStateStoreService.java,490,506,temp,NMLeveldbStateStoreService.java,457,471
//,3
public class xxx {
  @Override
  public void storeContainerDiagnostics(ContainerId containerId,
      StringBuilder diagnostics) throws IOException {
    if (LOG.isDebugEnabled()) {
      LOG.debug("storeContainerDiagnostics: containerId=" + containerId
          + ", diagnostics=" + diagnostics);
    }

    String key = CONTAINERS_KEY_PREFIX + containerId.toString()
        + CONTAINER_DIAGS_KEY_SUFFIX;
    try {
      db.put(bytes(key), bytes(diagnostics.toString()));
    } catch (DBException e) {
      markStoreUnHealthy(e);
      throw new IOException(e);
    }
  }

};