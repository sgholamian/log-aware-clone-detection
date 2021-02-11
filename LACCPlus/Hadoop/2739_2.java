//,temp,DisabledPlacementProcessor.java,61,76,temp,DisabledPlacementProcessor.java,40,59
//,3
public class xxx {
  @Override
  public void registerApplicationMaster(
      ApplicationAttemptId applicationAttemptId,
      RegisterApplicationMasterRequest request,
      RegisterApplicationMasterResponse response)
      throws IOException, YarnException {
    if (request.getPlacementConstraints() != null && !request
        .getPlacementConstraints().isEmpty()) {
      String message = "Found non empty placement constraints map in "
          + "RegisterApplicationMasterRequest for application="
          + applicationAttemptId.toString() + ", but the configured "
          + YarnConfiguration.RM_PLACEMENT_CONSTRAINTS_HANDLER
          + " cannot handle placement constraints. Rejecting this "
          + "registerApplicationMaster operation";
      LOG.warn(message);
      throw new YarnException(message);
    }
    nextAMSProcessor.registerApplicationMaster(applicationAttemptId, request,
        response);
  }

};