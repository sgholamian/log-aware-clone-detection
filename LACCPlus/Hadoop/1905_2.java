//,temp,MRClientService.java,325,344,temp,MRClientService.java,289,306
//,3
public class xxx {
    @SuppressWarnings("unchecked")
    @Override
    public KillJobResponse killJob(KillJobRequest request) 
      throws IOException {
      JobId jobId = request.getJobId();
      UserGroupInformation callerUGI = UserGroupInformation.getCurrentUser();
      String message = "Kill job " + jobId + " received from " + callerUGI
          + " at " + Server.getRemoteAddress();
      LOG.info(message);
      verifyAndGetJob(jobId, JobACL.MODIFY_JOB, false);
      appContext.getEventHandler().handle(
          new JobDiagnosticsUpdateEvent(jobId, message));
      appContext.getEventHandler().handle(
          new JobEvent(jobId, JobEventType.JOB_KILL));
      KillJobResponse response = 
        recordFactory.newRecordInstance(KillJobResponse.class);
      return response;
    }

};