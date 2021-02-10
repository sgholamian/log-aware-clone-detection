//,temp,TestDirectoryScanner.java,236,249,temp,TestDirectoryScanner.java,221,233
//,2
public class xxx {
  private long createMetaFile() throws IOException {
    long id = getFreeBlockId();
    try (FsDatasetSpi.FsVolumeReferences refs = fds.getFsVolumeReferences()) {
      int numVolumes = refs.size();
      int index = rand.nextInt(numVolumes - 1);

      File finalizedDir = refs.get(index).getFinalizedDir(bpid);
      File file = new File(finalizedDir, getMetaFile(id));
      if (file.createNewFile()) {
        LOG.info("Created metafile " + file.getName());
      }
    }
    return id;
  }

};