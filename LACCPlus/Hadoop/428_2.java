//,temp,ClientRMService.java,482,527,temp,ClientRMService.java,343,383
//,3
public class xxx {
  @Override
  public GetApplicationAttemptReportResponse getApplicationAttemptReport(
      GetApplicationAttemptReportRequest request) throws YarnException,
      IOException {
    ApplicationAttemptId appAttemptId = request.getApplicationAttemptId();
    UserGroupInformation callerUGI;
    try {
      callerUGI = UserGroupInformation.getCurrentUser();
    } catch (IOException ie) {
      LOG.info("Error getting UGI ", ie);
      throw RPCUtil.getRemoteException(ie);
    }
    RMApp application = this.rmContext.getRMApps().get(
        appAttemptId.getApplicationId());
    if (application == null) {
      // If the RM doesn't have the application, throw
      // ApplicationNotFoundException and let client to handle.
      throw new ApplicationNotFoundException("Application with id '"
          + request.getApplicationAttemptId().getApplicationId()
          + "' doesn't exist in RM.");
    }

    boolean allowAccess = checkAccess(callerUGI, application.getUser(),
        ApplicationAccessType.VIEW_APP, application);
    GetApplicationAttemptReportResponse response = null;
    if (allowAccess) {
      RMAppAttempt appAttempt = application.getAppAttempts().get(appAttemptId);
      if (appAttempt == null) {
        throw new ApplicationAttemptNotFoundException(
            "ApplicationAttempt with id '" + appAttemptId +
            "' doesn't exist in RM.");
      }
      ApplicationAttemptReport attemptReport = appAttempt
          .createApplicationAttemptReport();
      response = GetApplicationAttemptReportResponse.newInstance(attemptReport);
    }else{
      throw new YarnException("User " + callerUGI.getShortUserName()
          + " does not have privilage to see this attempt " + appAttemptId);
    }
    return response;
  }

};