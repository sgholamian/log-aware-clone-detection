//,temp,MRClientService.java,325,344,temp,MRClientService.java,308,323
//,3
public class xxx {
    @SuppressWarnings("unchecked")
    @Override
    public KillTaskResponse killTask(KillTaskRequest request) 
      throws IOException {
      TaskId taskId = request.getTaskId();
      UserGroupInformation callerUGI = UserGroupInformation.getCurrentUser();
      String message = "Kill task " + taskId + " received from " + callerUGI
          + " at " + Server.getRemoteAddress();
      LOG.info(message);
      verifyAndGetTask(taskId, JobACL.MODIFY_JOB);
      appContext.getEventHandler().handle(
          new TaskEvent(taskId, TaskEventType.T_KILL));
      KillTaskResponse response = 
        recordFactory.newRecordInstance(KillTaskResponse.class);
      return response;
    }

};