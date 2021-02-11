//,temp,DisabledPlacementProcessor.java,61,76,temp,DisabledPlacementProcessor.java,40,59
//,3
public class xxx {
  @Override
  public void allocate(ApplicationAttemptId appAttemptId,
      AllocateRequest request, AllocateResponse response) throws YarnException {
    if (request.getSchedulingRequests() != null && !request
        .getSchedulingRequests().isEmpty()) {
      String message = "Found non empty SchedulingRequest in "
          + "AllocateRequest for application="
          + appAttemptId.toString() + ", but the configured "
          + YarnConfiguration.RM_PLACEMENT_CONSTRAINTS_HANDLER
          + " cannot handle placement constraints. Rejecting this "
          + "allocate operation";
      LOG.warn(message);
      throw new YarnException(message);
    }
    nextAMSProcessor.allocate(appAttemptId, request, response);
  }

};