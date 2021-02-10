//,temp,DataStorage.java,740,799,temp,BlockPoolSliceStorage.java,419,468
//,3
public class xxx {
  void doUpgrade(DataNode datanode, StorageDirectory sd, NamespaceInfo nsInfo)
      throws IOException {
    // If the existing on-disk layout version supportes federation, simply
    // update its layout version.
    if (DataNodeLayoutVersion.supports(
        LayoutVersion.Feature.FEDERATION, layoutVersion)) {
      // The VERSION file is already read in. Override the layoutVersion 
      // field and overwrite the file. The upgrade work is handled by
      // {@link BlockPoolSliceStorage#doUpgrade}
      LOG.info("Updating layout version from " + layoutVersion + " to "
          + HdfsServerConstants.DATANODE_LAYOUT_VERSION + " for storage "
          + sd.getRoot());
      layoutVersion = HdfsServerConstants.DATANODE_LAYOUT_VERSION;
      writeProperties(sd);
      return;
    }
    
    LOG.info("Upgrading storage directory " + sd.getRoot()
             + ".\n   old LV = " + this.getLayoutVersion()
             + "; old CTime = " + this.getCTime()
             + ".\n   new LV = " + HdfsServerConstants.DATANODE_LAYOUT_VERSION
             + "; new CTime = " + nsInfo.getCTime());
    
    File curDir = sd.getCurrentDir();
    File prevDir = sd.getPreviousDir();
    File bbwDir = new File(sd.getRoot(), Storage.STORAGE_1_BBW);

    assert curDir.exists() : "Data node current directory must exist.";
    // Cleanup directory "detach"
    cleanupDetachDir(new File(curDir, STORAGE_DIR_DETACHED));
    
    // 1. delete <SD>/previous dir before upgrading
    if (prevDir.exists())
      deleteDir(prevDir);
    // get previous.tmp directory, <SD>/previous.tmp
    File tmpDir = sd.getPreviousTmp();
    assert !tmpDir.exists() : 
      "Data node previous.tmp directory must not exist.";
    
    // 2. Rename <SD>/current to <SD>/previous.tmp
    rename(curDir, tmpDir);
    
    // 3. Format BP and hard link blocks from previous directory
    File curBpDir = BlockPoolSliceStorage.getBpRoot(nsInfo.getBlockPoolID(), curDir);
    BlockPoolSliceStorage bpStorage = new BlockPoolSliceStorage(nsInfo.getNamespaceID(), 
        nsInfo.getBlockPoolID(), nsInfo.getCTime(), nsInfo.getClusterID());
    bpStorage.format(curDir, nsInfo);
    linkAllBlocks(datanode, tmpDir, bbwDir, new File(curBpDir,
        STORAGE_DIR_CURRENT));
    
    // 4. Write version file under <SD>/current
    layoutVersion = HdfsServerConstants.DATANODE_LAYOUT_VERSION;
    clusterID = nsInfo.getClusterID();
    writeProperties(sd);
    
    // 5. Rename <SD>/previous.tmp to <SD>/previous
    rename(tmpDir, prevDir);
    LOG.info("Upgrade of " + sd.getRoot()+ " is complete");
    addBlockPoolStorage(nsInfo.getBlockPoolID(), bpStorage);
  }

};