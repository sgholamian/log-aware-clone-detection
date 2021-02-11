//,temp,FileSystemRMStateStore.java,442,461,temp,FileSystemRMStateStore.java,424,440
//,3
public class xxx {
  @Override
  public synchronized void storeApplicationAttemptStateInternal(
      ApplicationAttemptId appAttemptId,
      ApplicationAttemptStateData attemptStateDataPB)
      throws Exception {
    Path appDirPath =
        getAppDir(rmAppRoot, appAttemptId.getApplicationId());
    Path nodeCreatePath = getNodePath(appDirPath, appAttemptId.toString());
    LOG.info("Storing info for attempt: " + appAttemptId + " at: "
        + nodeCreatePath);
    byte[] attemptStateData = attemptStateDataPB.getProto().toByteArray();
    try {
      // currently throw all exceptions. May need to respond differently for HA
      // based on whether we have lost the right to write to FS
      writeFileWithRetries(nodeCreatePath, attemptStateData, true);
    } catch (Exception e) {
      LOG.info("Error storing info for attempt: " + appAttemptId, e);
      throw e;
    }
  }

};