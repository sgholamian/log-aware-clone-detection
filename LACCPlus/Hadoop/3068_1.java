//,temp,TestDirectoryScanner.java,149,162,temp,TestDirectoryScanner.java,124,146
//,3
public class xxx {
  private long deleteBlockFile() {
    try(AutoCloseableLock lock = fds.acquireDatasetLock()) {
      for (ReplicaInfo b : FsDatasetTestUtil.getReplicas(fds, bpid)) {
        File f = new File(b.getBlockURI());
        File mf = new File(b.getMetadataURI());
        // Delete a block file that has corresponding metadata file
        if (f.exists() && mf.exists() && f.delete()) {
          LOG.info("Deleting block file " + f.getAbsolutePath());
          return b.getBlockId();
        }
      }
    }
    return 0;
  }

};