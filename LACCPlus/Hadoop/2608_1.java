//,temp,InMemoryStore.java,123,135,temp,MemoryPlacementConstraintManager.java,120,138
//,3
public class xxx {
  @Override public void addEstimation(String pipelineId,
      RLESparseResourceAllocation resourceSkyline)
      throws SkylineStoreException {
    inputValidator.validate(pipelineId, resourceSkyline);
    writeLock.lock();
    try {
      estimationStore.put(pipelineId, resourceSkyline);
      LOGGER.info("Successfully add estimated resource allocation for {}.",
          pipelineId);
    } finally {
      writeLock.unlock();
    }
  }

};