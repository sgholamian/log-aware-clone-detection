//,temp,FairScheduler.java,644,653,temp,NameNode.java,955,962
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