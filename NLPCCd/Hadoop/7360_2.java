//,temp,sample_6730.java,2,9,temp,sample_60.java,2,9
//,3
public class xxx {
private synchronized void doneApplication(ApplicationId applicationId, RMAppState finalState) {
SchedulerApplication<FifoAppAttempt> application = applications.get(applicationId);
if (application == null){


log.info("couldn t find application");
}
}

};