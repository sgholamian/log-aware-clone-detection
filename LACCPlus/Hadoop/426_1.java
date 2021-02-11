//,temp,DataStorage.java,356,400,temp,DataStorage.java,309,343
//,3
public class xxx {
  @VisibleForTesting
  synchronized List<StorageLocation> addStorageLocations(DataNode datanode,
      NamespaceInfo nsInfo, Collection<StorageLocation> dataDirs,
      StartupOption startOpt) throws IOException {
    final String bpid = nsInfo.getBlockPoolID();
    List<StorageLocation> successVolumes = Lists.newArrayList();
    for (StorageLocation dataDir : dataDirs) {
      File root = dataDir.getFile();
      if (!containsStorageDir(root)) {
        try {
          // It first ensures the datanode level format is completed.
          StorageDirectory sd = loadStorageDirectory(
              datanode, nsInfo, root, startOpt);
          addStorageDir(sd);
        } catch (IOException e) {
          LOG.warn(e);
          continue;
        }
      } else {
        LOG.info("Storage directory " + dataDir + " has already been used.");
      }

      List<File> bpDataDirs = new ArrayList<File>();
      bpDataDirs.add(BlockPoolSliceStorage.getBpRoot(bpid, new File(root,
              STORAGE_DIR_CURRENT)));
      try {
        makeBlockPoolDataDir(bpDataDirs, null);
        BlockPoolSliceStorage bpStorage = this.bpStorageMap.get(bpid);
        if (bpStorage == null) {
          bpStorage = new BlockPoolSliceStorage(
              nsInfo.getNamespaceID(), bpid, nsInfo.getCTime(),
              nsInfo.getClusterID());
        }

        bpStorage.recoverTransitionRead(datanode, nsInfo, bpDataDirs, startOpt);
        addBlockPoolStorage(bpid, bpStorage);
      } catch (IOException e) {
        LOG.warn("Failed to add storage for block pool: " + bpid + " : "
            + e.getMessage());
        continue;
      }
      successVolumes.add(dataDir);
    }
    return successVolumes;
  }

};