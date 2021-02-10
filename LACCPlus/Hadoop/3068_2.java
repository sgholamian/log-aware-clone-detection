//,temp,TestDirectoryScanner.java,149,162,temp,TestDirectoryScanner.java,124,146
//,3
public class xxx {
  private long truncateBlockFile() throws IOException {
    try(AutoCloseableLock lock = fds.acquireDatasetLock()) {
      for (ReplicaInfo b : FsDatasetTestUtil.getReplicas(fds, bpid)) {
        File f = new File(b.getBlockURI());
        File mf = new File(b.getMetadataURI());
        // Truncate a block file that has a corresponding metadata file
        if (f.exists() && f.length() != 0 && mf.exists()) {
          FileOutputStream s = null;
          FileChannel channel = null;
          try {
            s = new FileOutputStream(f);
            channel = s.getChannel();
            channel.truncate(0);
            LOG.info("Truncated block file " + f.getAbsolutePath());
            return b.getBlockId();
          } finally {
            IOUtils.cleanup(LOG, channel, s);
          }
        }
      }
    }
    return 0;
  }

};