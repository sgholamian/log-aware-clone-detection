//,temp,MRClientService.java,358,377,temp,MRClientService.java,289,306
//,3
public class xxx {
    @SuppressWarnings("unchecked")
    @Override
    public FailTaskAttemptResponse failTaskAttempt(
        FailTaskAttemptRequest request) throws IOException {
      TaskAttemptId taskAttemptId = request.getTaskAttemptId();
      UserGroupInformation callerUGI = UserGroupInformation.getCurrentUser();
      String message = "Fail task attempt " + taskAttemptId
          + " received from " + callerUGI + " at "
          + Server.getRemoteAddress();
      LOG.info(message);
      verifyAndGetAttempt(taskAttemptId, JobACL.MODIFY_JOB);
      appContext.getEventHandler().handle(
          new TaskAttemptDiagnosticsUpdateEvent(taskAttemptId, message));
      appContext.getEventHandler().handle(
          new TaskAttemptEvent(taskAttemptId, 
              TaskAttemptEventType.TA_FAILMSG_BY_CLIENT));
      FailTaskAttemptResponse response = recordFactory.
        newRecordInstance(FailTaskAttemptResponse.class);
      return response;
    }

};