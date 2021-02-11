//,temp,NNUpgradeUtil.java,200,223,temp,NNUpgradeUtil.java,84,99
//,3
public class xxx {
  static void doFinalize(StorageDirectory sd) throws IOException {
    File prevDir = sd.getPreviousDir();
    if (!prevDir.exists()) { // already discarded
      LOG.info("Directory " + prevDir + " does not exist.");
      LOG.info("Finalize upgrade for " + sd.getRoot()+ " is not required.");
      return;
    }
    LOG.info("Finalizing upgrade of storage directory " + sd.getRoot());
    Preconditions.checkState(sd.getCurrentDir().exists(),
        "Current directory must exist.");
    final File tmpDir = sd.getFinalizedTmp();
    // rename previous to tmp and remove
    NNStorage.rename(prevDir, tmpDir);
    NNStorage.deleteDir(tmpDir);
    LOG.info("Finalize upgrade for " + sd.getRoot()+ " is complete.");
  }

};