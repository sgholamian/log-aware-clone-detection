//,temp,DataStorage.java,842,885,temp,BlockPoolSliceStorage.java,552,591
//,3
public class xxx {
  void doRollback( StorageDirectory sd,
                   NamespaceInfo nsInfo
                   ) throws IOException {
    File prevDir = sd.getPreviousDir();
    // This is a regular startup or a post-federation rollback
    if (!prevDir.exists()) {
      if (DataNodeLayoutVersion.supports(LayoutVersion.Feature.FEDERATION,
          HdfsServerConstants.DATANODE_LAYOUT_VERSION)) {
        readProperties(sd, HdfsServerConstants.DATANODE_LAYOUT_VERSION);
        writeProperties(sd);
        LOG.info("Layout version rolled back to "
            + HdfsServerConstants.DATANODE_LAYOUT_VERSION + " for storage "
            + sd.getRoot());
      }
      return;
    }
    DataStorage prevInfo = new DataStorage();
    prevInfo.readPreviousVersionProperties(sd);

    // We allow rollback to a state, which is either consistent with
    // the namespace state or can be further upgraded to it.
    if (!(prevInfo.getLayoutVersion() >= HdfsServerConstants.DATANODE_LAYOUT_VERSION
          && prevInfo.getCTime() <= nsInfo.getCTime()))  // cannot rollback
      throw new InconsistentFSStateException(sd.getRoot(),
          "Cannot rollback to a newer state.\nDatanode previous state: LV = "
              + prevInfo.getLayoutVersion() + " CTime = " + prevInfo.getCTime()
              + " is newer than the namespace state: LV = "
              + HdfsServerConstants.DATANODE_LAYOUT_VERSION + " CTime = "
              + nsInfo.getCTime());
    LOG.info("Rolling back storage directory " + sd.getRoot()
        + ".\n   target LV = " + HdfsServerConstants.DATANODE_LAYOUT_VERSION
        + "; target CTime = " + nsInfo.getCTime());
    File tmpDir = sd.getRemovedTmp();
    assert !tmpDir.exists() : "removed.tmp directory must not exist.";
    // rename current to tmp
    File curDir = sd.getCurrentDir();
    assert curDir.exists() : "Current directory must exist.";
    rename(curDir, tmpDir);
    // rename previous to current
    rename(prevDir, curDir);
    // delete tmp dir
    deleteDir(tmpDir);
    LOG.info("Rollback of " + sd.getRoot() + " is complete");
  }

};