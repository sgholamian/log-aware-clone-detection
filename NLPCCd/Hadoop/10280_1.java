//,temp,sample_8832.java,2,19,temp,sample_8842.java,2,18
//,3
public class xxx {
public void dummy_method(){
String path = getNodePath(appDirPath, appAttemptId.toString());
byte[] attemptStateData = (attemptStateDataPB == null) ? null : attemptStateDataPB.getProto().toByteArray();
if (LOG.isDebugEnabled()) {
}
switch (operation) {
case UPDATE: if (exists(path)) {
zkManager.safeSetData(path, attemptStateData, -1, zkAcl, fencingNodePath);
} else {
zkManager.safeCreate(path, attemptStateData, zkAcl, CreateMode.PERSISTENT, zkAcl, fencingNodePath);
if (LOG.isDebugEnabled()) {


log.info("path for didn t exist created a new znode to update the application attempt state");
}
}
}
}

};