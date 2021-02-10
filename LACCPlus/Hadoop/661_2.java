//,temp,TestDirectoryScanner.java,141,153,temp,TestDirectoryScanner.java,125,138
//,3
public class xxx {
  private long deleteBlockFile() {
    synchronized(fds) {
      for (ReplicaInfo b : FsDatasetTestUtil.getReplicas(fds, bpid)) {
        File f = b.getBlockFile();
        File mf = b.getMetaFile();
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