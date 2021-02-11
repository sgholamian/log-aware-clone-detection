//,temp,DataStorage.java,356,400,temp,DataStorage.java,309,343
//,3
public class xxx {
  public VolumeBuilder prepareVolume(DataNode datanode, File volume,
      List<NamespaceInfo> nsInfos) throws IOException {
    if (containsStorageDir(volume)) {
      final String errorMessage = "Storage directory is in use";
      LOG.warn(errorMessage + ".");
      throw new IOException(errorMessage);
    }

    StorageDirectory sd = loadStorageDirectory(
        datanode, nsInfos.get(0), volume, StartupOption.HOTSWAP);
    VolumeBuilder builder =
        new VolumeBuilder(this, sd);
    for (NamespaceInfo nsInfo : nsInfos) {
      List<File> bpDataDirs = Lists.newArrayList();
      bpDataDirs.add(BlockPoolSliceStorage.getBpRoot(
          nsInfo.getBlockPoolID(), new File(volume, STORAGE_DIR_CURRENT)));
      makeBlockPoolDataDir(bpDataDirs, null);

      BlockPoolSliceStorage bpStorage;
      final String bpid = nsInfo.getBlockPoolID();
      synchronized (this) {
        bpStorage = this.bpStorageMap.get(bpid);
        if (bpStorage == null) {
          bpStorage = new BlockPoolSliceStorage(
              nsInfo.getNamespaceID(), bpid, nsInfo.getCTime(),
              nsInfo.getClusterID());
          addBlockPoolStorage(bpid, bpStorage);
        }
      }
      builder.addBpStorageDirectories(
          bpid, bpStorage.loadBpStorageDirectories(
              datanode, nsInfo, bpDataDirs, StartupOption.HOTSWAP));
    }
    return builder;
  }

};