//,temp,ZKRMStateStore.java,632,655,temp,ZKRMStateStore.java,593,612
//,3
public class xxx {
  @Override
  public synchronized void updateApplicationStateInternal(ApplicationId appId,
      ApplicationStateData appStateDataPB) throws Exception {
    String nodeUpdatePath = getNodePath(rmAppRoot, appId.toString());

    if (LOG.isDebugEnabled()) {
      LOG.debug("Storing final state info for app: " + appId + " at: "
          + nodeUpdatePath);
    }
    byte[] appStateData = appStateDataPB.getProto().toByteArray();

    if (exists(nodeUpdatePath)) {
      safeSetData(nodeUpdatePath, appStateData, -1);
    } else {
      safeCreate(nodeUpdatePath, appStateData, zkAcl,
          CreateMode.PERSISTENT);
      LOG.debug(appId + " znode didn't exist. Created a new znode to"
          + " update the application state.");
    }
  }

};