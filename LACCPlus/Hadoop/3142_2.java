//,temp,DataStorage.java,903,945,temp,BlockPoolSliceStorage.java,594,634
//,3
public class xxx {
  void doRollback(StorageDirectory bpSd, NamespaceInfo nsInfo)
      throws IOException {
    File prevDir = bpSd.getPreviousDir();
    // regular startup if previous dir does not exist
    if (prevDir == null || !prevDir.exists()) {
      return;
    }
    // read attributes out of the VERSION file of previous directory
    BlockPoolSliceStorage prevInfo = new BlockPoolSliceStorage();
    prevInfo.readPreviousVersionProperties(bpSd);

    // We allow rollback to a state, which is either consistent with
    // the namespace state or can be further upgraded to it.
    // In another word, we can only roll back when ( storedLV >= software LV)
    // && ( DN.previousCTime <= NN.ctime)
    if (!(prevInfo.getLayoutVersion() >= HdfsServerConstants.DATANODE_LAYOUT_VERSION &&
        prevInfo.getCTime() <= nsInfo.getCTime())) { // cannot rollback
      throw new InconsistentFSStateException(bpSd.getRoot(),
          "Cannot rollback to a newer state.\nDatanode previous state: LV = "
              + prevInfo.getLayoutVersion() + " CTime = " + prevInfo.getCTime()
              + " is newer than the namespace state: LV = "
              + HdfsServerConstants.DATANODE_LAYOUT_VERSION + " CTime = " + nsInfo.getCTime());
    }

    LOG.info("Rolling back storage directory {}.\n   target LV = {}; target "
            + "CTime = {}", bpSd.getRoot(), nsInfo.getLayoutVersion(),
        nsInfo.getCTime());
    File tmpDir = bpSd.getRemovedTmp();
    assert !tmpDir.exists() : "removed.tmp directory must not exist.";
    // 1. rename current to tmp
    File curDir = bpSd.getCurrentDir();
    assert curDir.exists() : "Current directory must exist.";
    rename(curDir, tmpDir);
    
    // 2. rename previous to current
    rename(prevDir, curDir);
    
    // 3. delete removed.tmp dir
    deleteDir(tmpDir);
    LOG.info("Rollback of {} is complete", bpSd.getRoot());
  }

};