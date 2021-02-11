//,temp,FileSystemRMStateStore.java,463,482,temp,FileSystemRMStateStore.java,405,422
//,3
public class xxx {
  @Override
  public synchronized void storeApplicationStateInternal(ApplicationId appId,
      ApplicationStateData appStateDataPB) throws Exception {
    Path appDirPath = getAppDir(rmAppRoot, appId);
    mkdirsWithRetries(appDirPath);
    Path nodeCreatePath = getNodePath(appDirPath, appId.toString());

    LOG.info("Storing info for app: " + appId + " at: " + nodeCreatePath);
    byte[] appStateData = appStateDataPB.getProto().toByteArray();
    try {
      // currently throw all exceptions. May need to respond differently for HA
      // based on whether we have lost the right to write to FS
      writeFileWithRetries(nodeCreatePath, appStateData, true);
    } catch (Exception e) {
      LOG.info("Error storing info for app: " + appId, e);
      throw e;
    }
  }

};