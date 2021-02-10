//,temp,MRClientService.java,325,344,temp,MRClientService.java,289,306
//,3
public class xxx {
    @SuppressWarnings("unchecked")
    @Override
    public KillTaskAttemptResponse killTaskAttempt(
        KillTaskAttemptRequest request) throws IOException {
      TaskAttemptId taskAttemptId = request.getTaskAttemptId();
      UserGroupInformation callerUGI = UserGroupInformation.getCurrentUser();
      String message = "Kill task attempt " + taskAttemptId
          + " received from " + callerUGI + " at "
          + Server.getRemoteAddress();
      LOG.info(message);
      verifyAndGetAttempt(taskAttemptId, JobACL.MODIFY_JOB);
      appContext.getEventHandler().handle(
          new TaskAttemptDiagnosticsUpdateEvent(taskAttemptId, message));
      appContext.getEventHandler().handle(
          new TaskAttemptEvent(taskAttemptId, 
              TaskAttemptEventType.TA_KILL));
      KillTaskAttemptResponse response = 
        recordFactory.newRecordInstance(KillTaskAttemptResponse.class);
      return response;
    }

};