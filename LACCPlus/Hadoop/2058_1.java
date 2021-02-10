//,temp,ClientRMService.java,385,423,temp,ClientRMService.java,343,383
//,3
public class xxx {
  @Override
  public GetApplicationAttemptsResponse getApplicationAttempts(
      GetApplicationAttemptsRequest request) throws YarnException, IOException {
    ApplicationId appId = request.getApplicationId();
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
    GetApplicationAttemptsResponse response = null;
    if (allowAccess) {
      Map<ApplicationAttemptId, RMAppAttempt> attempts = application
          .getAppAttempts();
      List<ApplicationAttemptReport> listAttempts = 
        new ArrayList<ApplicationAttemptReport>();
      Iterator<Map.Entry<ApplicationAttemptId, RMAppAttempt>> iter = attempts
          .entrySet().iterator();
      while (iter.hasNext()) {
        listAttempts.add(iter.next().getValue()
            .createApplicationAttemptReport());
      }
      response = GetApplicationAttemptsResponse.newInstance(listAttempts);
    } else {
      throw new YarnException("User " + callerUGI.getShortUserName()
          + " does not have privilage to see this aplication " + appId);
    }
    return response;
  }

};