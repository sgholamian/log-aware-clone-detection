//,temp,DataStorage.java,740,799,temp,BlockPoolSliceStorage.java,419,468
//,3
public class xxx {
  void doUpgrade(DataNode datanode, StorageDirectory bpSd, NamespaceInfo nsInfo)
      throws IOException {
    // Upgrading is applicable only to release with federation or after
    if (!DataNodeLayoutVersion.supports(
        LayoutVersion.Feature.FEDERATION, layoutVersion)) {
      return;
    }
    LOG.info("Upgrading block pool storage directory " + bpSd.getRoot()
        + ".\n   old LV = " + this.getLayoutVersion() + "; old CTime = "
        + this.getCTime() + ".\n   new LV = " + HdfsServerConstants.DATANODE_LAYOUT_VERSION
        + "; new CTime = " + nsInfo.getCTime());
    // get <SD>/previous directory
    String dnRoot = getDataNodeStorageRoot(bpSd.getRoot().getCanonicalPath());
    StorageDirectory dnSdStorage = new StorageDirectory(new File(dnRoot));
    File dnPrevDir = dnSdStorage.getPreviousDir();
    
    // If <SD>/previous directory exists delete it
    if (dnPrevDir.exists()) {
      deleteDir(dnPrevDir);
    }
    File bpCurDir = bpSd.getCurrentDir();
    File bpPrevDir = bpSd.getPreviousDir();
    assert bpCurDir.exists() : "BP level current directory must exist.";
    cleanupDetachDir(new File(bpCurDir, DataStorage.STORAGE_DIR_DETACHED));
    
    // 1. Delete <SD>/current/<bpid>/previous dir before upgrading
    if (bpPrevDir.exists()) {
      deleteDir(bpPrevDir);
    }
    File bpTmpDir = bpSd.getPreviousTmp();
    assert !bpTmpDir.exists() : "previous.tmp directory must not exist.";
    
    // 2. Rename <SD>/current/<bpid>/current to
    //    <SD>/current/<bpid>/previous.tmp
    rename(bpCurDir, bpTmpDir);
    
    // 3. Create new <SD>/current with block files hardlinks and VERSION
    linkAllBlocks(datanode, bpTmpDir, bpCurDir);
    this.layoutVersion = HdfsServerConstants.DATANODE_LAYOUT_VERSION;
    assert this.namespaceID == nsInfo.getNamespaceID() 
        : "Data-node and name-node layout versions must be the same.";
    this.cTime = nsInfo.getCTime();
    writeProperties(bpSd);
    
    // 4.rename <SD>/current/<bpid>/previous.tmp to
    // <SD>/current/<bpid>/previous
    rename(bpTmpDir, bpPrevDir);
    LOG.info("Upgrade of block pool " + blockpoolID + " at " + bpSd.getRoot()
        + " is complete");
  }

};