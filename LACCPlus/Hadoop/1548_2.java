//,temp,FileSystemRMStateStore.java,463,482,temp,FileSystemRMStateStore.java,424,440
//,3
public class xxx {
  @Override
  public synchronized void updateApplicationStateInternal(ApplicationId appId,
      ApplicationStateData appStateDataPB) throws Exception {
    Path appDirPath = getAppDir(rmAppRoot, appId);
    Path nodeCreatePath = getNodePath(appDirPath, appId.toString());

    LOG.info("Updating info for app: " + appId + " at: " + nodeCreatePath);
    byte[] appStateData = appStateDataPB.getProto().toByteArray();
    try {
      // currently throw all exceptions. May need to respond differently for HA
      // based on whether we have lost the right to write to FS
      updateFile(nodeCreatePath, appStateData, true);
    } catch (Exception e) {
      LOG.info("Error updating info for app: " + appId, e);
      throw e;
    }
  }

};