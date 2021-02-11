//,temp,DataStorage.java,896,931,temp,BlockPoolSliceStorage.java,597,632
//,3
public class xxx {
  void doFinalize(File dnCurDir) throws IOException {
    File bpRoot = getBpRoot(blockpoolID, dnCurDir);
    StorageDirectory bpSd = new StorageDirectory(bpRoot);
    // block pool level previous directory
    File prevDir = bpSd.getPreviousDir();
    if (!prevDir.exists()) {
      return; // already finalized
    }
    final String dataDirPath = bpSd.getRoot().getCanonicalPath();
    LOG.info("Finalizing upgrade for storage directory " + dataDirPath
        + ".\n   cur LV = " + this.getLayoutVersion() + "; cur CTime = "
        + this.getCTime());
    assert bpSd.getCurrentDir().exists() : "Current directory must exist.";
    
    // rename previous to finalized.tmp
    final File tmpDir = bpSd.getFinalizedTmp();
    rename(prevDir, tmpDir);

    // delete finalized.tmp dir in a separate thread
    new Daemon(new Runnable() {
      @Override
      public void run() {
        try {
          deleteDir(tmpDir);
        } catch (IOException ex) {
          LOG.error("Finalize upgrade for " + dataDirPath + " failed.", ex);
        }
        LOG.info("Finalize upgrade for " + dataDirPath + " is complete.");
      }

      @Override
      public String toString() {
        return "Finalize " + dataDirPath;
      }
    }).start();
  }

};