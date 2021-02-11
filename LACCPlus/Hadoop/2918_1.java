//,temp,SkylineStoreValidator.java,107,117,temp,SkylineStoreValidator.java,67,76
//,3
public class xxx {
  public final void validate(final String pipelineId,
      final RLESparseResourceAllocation resourceOverTime)
      throws SkylineStoreException {
    validate(pipelineId);
    if (resourceOverTime == null) {
      StringBuilder sb = new StringBuilder();
      sb.append("Resource allocation for " + pipelineId + " is null.");
      LOGGER.error(sb.toString());
      throw new NullRLESparseResourceAllocationException(sb.toString());
    }
  }

};