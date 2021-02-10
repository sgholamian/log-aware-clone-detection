//,temp,ClientRMService.java,482,527,temp,ClientRMService.java,385,423
//,3
public class xxx {
  @Override
  public GetContainersResponse getContainers(GetContainersRequest request)
      throws YarnException, IOException {
    ApplicationAttemptId appAttemptId = request.getApplicationAttemptId();
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
    GetContainersResponse response = null;
    if (allowAccess) {
      RMAppAttempt appAttempt = application.getAppAttempts().get(appAttemptId);
      if (appAttempt == null) {
        throw new ApplicationAttemptNotFoundException(
            "ApplicationAttempt with id '" + appAttemptId +
            "' doesn't exist in RM.");
      }
      Collection<RMContainer> rmContainers = Collections.emptyList();
      SchedulerAppReport schedulerAppReport =
          this.rmContext.getScheduler().getSchedulerAppInfo(appAttemptId);
      if (schedulerAppReport != null) {
        rmContainers = schedulerAppReport.getLiveContainers();
      }
      List<ContainerReport> listContainers = new ArrayList<ContainerReport>();
      for (RMContainer rmContainer : rmContainers) {
        listContainers.add(rmContainer.createContainerReport());
      }
      response = GetContainersResponse.newInstance(listContainers);
    } else {
      throw new YarnException("User " + callerUGI.getShortUserName()
          + " does not have privilage to see this aplication " + appId);
    }
    return response;
  }

};