//,temp,FairScheduler.java,644,653,temp,ServiceOperations.java,100,108
//,3
public class xxx {
  private void removeApplication(ApplicationId applicationId,
      RMAppState finalState) {
    SchedulerApplication<FSAppAttempt> application = applications.remove(
        applicationId);
    if (application == null) {
      LOG.warn("Couldn't find application " + applicationId);
    } else{
      application.stop(finalState);
    }
  }

};