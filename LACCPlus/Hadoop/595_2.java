//,temp,TestDirectoryScanner.java,236,249,temp,TestDirectoryScanner.java,221,233
//,2
public class xxx {
  private long createBlockFile() throws IOException {
    long id = getFreeBlockId();
    try (FsDatasetSpi.FsVolumeReferences volumes = fds.getFsVolumeReferences()) {
      int numVolumes = volumes.size();
      int index = rand.nextInt(numVolumes - 1);
      File finalizedDir = volumes.get(index).getFinalizedDir(bpid);
      File file = new File(finalizedDir, getBlockFile(id));
      if (file.createNewFile()) {
        LOG.info("Created block file " + file.getName());
      }
    }
    return id;
  }

};