//,temp,sample_5493.java,2,11,temp,sample_1342.java,2,12
//,3
public class xxx {
public synchronized void appFinished(ApplicationId appId) {
List<ApplicationAttemptId> appAttemptList = appToAppAttemptMap.get(appId);
if (appAttemptList != null) {
if (LOG.isDebugEnabled()) {


log.info("removing application attempts nmtoken keys for application");
}
}
}

};