//,temp,SkylineStoreValidator.java,107,117,temp,SkylineStoreValidator.java,67,76
//,3
public class xxx {
  public final void validate(final String pipelineId)
      throws SkylineStoreException {
    if (pipelineId == null) {
      StringBuilder sb = new StringBuilder();
      sb.append("pipelineId is null, please try again by specifying"
          + " a valid pipelineId.");
      LOGGER.error(sb.toString());
      throw new NullPipelineIdException(sb.toString());
    }
  }

};