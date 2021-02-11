//,temp,TestCacheDirectives.java,822,881,temp,TestCacheDirectives.java,776,820
//,3
public class xxx {
  private static void waitForCachePoolStats(final DistributedFileSystem dfs,
      final long targetBytesNeeded, final long targetBytesCached,
      final long targetFilesNeeded, final long targetFilesCached,
      final CachePoolInfo pool, final String infoString)
            throws Exception {
    LOG.info("Polling listCachePools " + pool.toString() + " for " +
        targetBytesNeeded + " targetBytesNeeded, " +
        targetBytesCached + " targetBytesCached, " +
        targetFilesNeeded + " targetFilesNeeded, " +
        targetFilesCached + " targetFilesCached");
    GenericTestUtils.waitFor(new Supplier<Boolean>() {
      @Override
      public Boolean get() {
        RemoteIterator<CachePoolEntry> iter = null;
        try {
          iter = dfs.listCachePools();
        } catch (IOException e) {
          fail("got IOException while calling " +
              "listCachePools: " + e.getMessage());
        }
        while (true) {
          CachePoolEntry entry = null;
          try {
            if (!iter.hasNext()) {
              break;
            }
            entry = iter.next();
          } catch (IOException e) {
            fail("got IOException while iterating through " +
                "listCachePools: " + e.getMessage());
          }
          if (entry == null) {
            break;
          }
          if (!entry.getInfo().getPoolName().equals(pool.getPoolName())) {
            continue;
          }
          CachePoolStats stats = entry.getStats();
          if ((targetBytesNeeded == stats.getBytesNeeded()) &&
              (targetBytesCached == stats.getBytesCached()) &&
              (targetFilesNeeded == stats.getFilesNeeded()) &&
              (targetFilesCached == stats.getFilesCached())) {
            return true;
          } else {
            LOG.info(infoString + ": " +
                "filesNeeded: " +
                stats.getFilesNeeded() + "/" + targetFilesNeeded +
                ", filesCached: " + 
                stats.getFilesCached() + "/" + targetFilesCached +
                ", bytesNeeded: " +
                stats.getBytesNeeded() + "/" + targetBytesNeeded +
                ", bytesCached: " + 
                stats.getBytesCached() + "/" + targetBytesCached);
            return false;
          }
        }
        return false;
      }
    }, 500, 60000);
  }

};