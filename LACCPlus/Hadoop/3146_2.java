//,temp,LeveldbRMStateStore.java,710,740,temp,NMLeveldbStateStoreService.java,527,555
//,3
public class xxx {
  @Override
  public void storeContainerUpdateToken(ContainerId containerId,
      ContainerTokenIdentifier containerTokenIdentifier) throws IOException {
    if (LOG.isDebugEnabled()) {
      LOG.debug("storeContainerUpdateToken: containerId=" + containerId);
    }

    String keyUpdateToken = CONTAINERS_KEY_PREFIX + containerId.toString()
        + CONTAINER_UPDATE_TOKEN_SUFFIX;
    String keyVersion = CONTAINERS_KEY_PREFIX + containerId.toString()
        + CONTAINER_VERSION_KEY_SUFFIX;

    try {
      WriteBatch batch = db.createWriteBatch();
      try {
        // New value will overwrite old values for the same key
        batch.put(bytes(keyUpdateToken),
            containerTokenIdentifier.getProto().toByteArray());
        batch.put(bytes(keyVersion),
            bytes(Integer.toString(containerTokenIdentifier.getVersion())));
        db.write(batch);
      } finally {
        batch.close();
      }
    } catch (DBException e) {
      markStoreUnHealthy(e);
      throw new IOException(e);
    }
  }

};