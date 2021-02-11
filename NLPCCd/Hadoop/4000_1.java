//,temp,sample_5156.java,2,8,temp,sample_5153.java,2,8
//,3
public class xxx {
public synchronized void removeApplicationStateInternal( ApplicationStateData appState) throws Exception {
ApplicationId appId = appState.getApplicationSubmissionContext().getApplicationId();
Path nodeRemovePath = getAppDir(rmAppRoot, appId);


log.info("removing info for app at");
}

};