//,temp,DataStorage.java,956,990,temp,BlockPoolSliceStorage.java,640,678
//,3
public class xxx {
  void doFinalize(File dnCurDir) throws IOException {
    if (dnCurDir == null) {
      return; //we do nothing if the directory is null
    }
    File bpRoot = getBpRoot(blockpoolID, dnCurDir);
    StorageDirectory bpSd = new StorageDirectory(bpRoot);
    // block pool level previous directory
    File prevDir = bpSd.getPreviousDir();
    if (!prevDir.exists()) {
      return; // already finalized
    }
    final String dataDirPath = bpSd.getRoot().getCanonicalPath();
    LOG.info("Finalizing upgrade for storage directory {}.\n   cur LV = {}; "
            + "cur CTime = {}", dataDirPath, this.getLayoutVersion(),
        this.getCTime());
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
          LOG.error("Finalize upgrade for {} failed.", dataDirPath, ex);
        }
        LOG.info("Finalize upgrade for {} is complete.", dataDirPath);
      }

      @Override
      public String toString() {
        return "Finalize " + dataDirPath;
      }
    }).start();
  }

};