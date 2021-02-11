//,temp,NNUpgradeUtil.java,200,223,temp,NNUpgradeUtil.java,84,99
//,3
public class xxx {
  static void doRollBack(StorageDirectory sd)
      throws IOException {
    File prevDir = sd.getPreviousDir();
    if (!prevDir.exists()) {
      return;
    }

    File tmpDir = sd.getRemovedTmp();
    Preconditions.checkState(!tmpDir.exists(),
        "removed.tmp directory must not exist for rollback."
            + "Consider restarting for recovery.");
    // rename current to tmp
    File curDir = sd.getCurrentDir();
    Preconditions.checkState(curDir.exists(),
        "Current directory must exist for rollback.");

    NNStorage.rename(curDir, tmpDir);
    // rename previous to current
    NNStorage.rename(prevDir, curDir);

    // delete tmp dir
    NNStorage.deleteDir(tmpDir);
    LOG.info("Rollback of " + sd.getRoot() + " is complete.");
  }

};