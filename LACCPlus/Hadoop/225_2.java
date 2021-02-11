//,temp,ZKRMStateStore.java,614,630,temp,ZKRMStateStore.java,579,591
//,3
public class xxx {
  @Override
  public synchronized void storeApplicationStateInternal(ApplicationId appId,
      ApplicationStateData appStateDataPB) throws Exception {
    String nodeCreatePath = getNodePath(rmAppRoot, appId.toString());

    if (LOG.isDebugEnabled()) {
      LOG.debug("Storing info for app: " + appId + " at: " + nodeCreatePath);
    }
    byte[] appStateData = appStateDataPB.getProto().toByteArray();
    safeCreate(nodeCreatePath, appStateData, zkAcl,
        CreateMode.PERSISTENT);

  }

};