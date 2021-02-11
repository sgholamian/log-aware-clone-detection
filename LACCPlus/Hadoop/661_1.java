//,temp,TestDirectoryScanner.java,141,153,temp,TestDirectoryScanner.java,125,138
//,3
public class xxx {
  private long deleteMetaFile() {
    synchronized(fds) {
      for (ReplicaInfo b : FsDatasetTestUtil.getReplicas(fds, bpid)) {
        File file = b.getMetaFile();
        // Delete a metadata file
        if (file.exists() && file.delete()) {
          LOG.info("Deleting metadata file " + file.getAbsolutePath());
          return b.getBlockId();
        }
      }
    }
    return 0;
  }

};