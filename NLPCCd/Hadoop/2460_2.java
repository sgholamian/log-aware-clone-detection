//,temp,sample_5153.java,2,8,temp,sample_5151.java,2,8
//,2
public class xxx {
public synchronized void storeApplicationAttemptStateInternal( ApplicationAttemptId appAttemptId, ApplicationAttemptStateData attemptStateDataPB) throws Exception {
Path appDirPath = getAppDir(rmAppRoot, appAttemptId.getApplicationId());
Path nodeCreatePath = getNodePath(appDirPath, appAttemptId.toString());


log.info("storing info for attempt at");
}

};