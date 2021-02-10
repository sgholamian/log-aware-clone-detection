//,temp,FileSystemRMStateStore.java,463,482,temp,FileSystemRMStateStore.java,424,440
//,3
public class xxx {
  @Override
  public synchronized void updateApplicationAttemptStateInternal(
      ApplicationAttemptId appAttemptId,
      ApplicationAttemptStateData attemptStateDataPB)
      throws Exception {
    Path appDirPath =
        getAppDir(rmAppRoot, appAttemptId.getApplicationId());
    Path nodeCreatePath = getNodePath(appDirPath, appAttemptId.toString());
    LOG.info("Updating info for attempt: " + appAttemptId + " at: "
        + nodeCreatePath);
    byte[] attemptStateData = attemptStateDataPB.getProto().toByteArray();
    try {
      // currently throw all exceptions. May need to respond differently for HA
      // based on whether we have lost the right to write to FS
      updateFile(nodeCreatePath, attemptStateData, true);
    } catch (Exception e) {
      LOG.info("Error updating info for attempt: " + appAttemptId, e);
      throw e;
    }
  }

};