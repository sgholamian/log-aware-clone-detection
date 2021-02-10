//,temp,ClientRMService.java,431,474,temp,ClientRMService.java,385,423
//,3
public class xxx {
  @Override
  public GetContainerReportResponse getContainerReport(
      GetContainerReportRequest request) throws YarnException, IOException {
    ContainerId containerId = request.getContainerId();
    ApplicationAttemptId appAttemptId = containerId.getApplicationAttemptId();
    ApplicationId appId = appAttemptId.getApplicationId();
    UserGroupInformation callerUGI;
    try {
      callerUGI = UserGroupInformation.getCurrentUser();
    } catch (IOException ie) {
      LOG.info("Error getting UGI ", ie);
      throw RPCUtil.getRemoteException(ie);
    }
    RMApp application = this.rmContext.getRMApps().get(appId);
    if (application == null) {
      // If the RM doesn't have the application, throw
      // ApplicationNotFoundException and let client to handle.
      throw new ApplicationNotFoundException("Application with id '" + appId
          + "' doesn't exist in RM.");
    }
    boolean allowAccess = checkAccess(callerUGI, application.getUser(),
        ApplicationAccessType.VIEW_APP, application);
    GetContainerReportResponse response = null;
    if (allowAccess) {
      RMAppAttempt appAttempt = application.getAppAttempts().get(appAttemptId);
      if (appAttempt == null) {
        throw new ApplicationAttemptNotFoundException(
            "ApplicationAttempt with id '" + appAttemptId +
            "' doesn't exist in RM.");
      }
      RMContainer rmConatiner = this.rmContext.getScheduler().getRMContainer(
          containerId);
      if (rmConatiner == null) {
        throw new ContainerNotFoundException("Container with id '" + containerId
            + "' doesn't exist in RM.");
      }
      response = GetContainerReportResponse.newInstance(rmConatiner
          .createContainerReport());
    } else {
      throw new YarnException("User " + callerUGI.getShortUserName()
          + " does not have privilage to see this aplication " + appId);
    }
    return response;
  }

};