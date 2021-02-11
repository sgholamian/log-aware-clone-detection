//,temp,TestCacheDirectives.java,822,881,temp,TestCacheDirectives.java,776,820
//,3
public class xxx {
  private static void waitForCacheDirectiveStats(final DistributedFileSystem dfs,
      final long targetBytesNeeded, final long targetBytesCached,
      final long targetFilesNeeded, final long targetFilesCached,
      final CacheDirectiveInfo filter, final String infoString)
            throws Exception {
    LOG.info("Polling listCacheDirectives " + 
        ((filter == null) ? "ALL" : filter.toString()) + " for " +
        targetBytesNeeded + " targetBytesNeeded, " +
        targetBytesCached + " targetBytesCached, " +
        targetFilesNeeded + " targetFilesNeeded, " +
        targetFilesCached + " targetFilesCached");
    GenericTestUtils.waitFor(new Supplier<Boolean>() {
      @Override
      public Boolean get() {
        RemoteIterator<CacheDirectiveEntry> iter = null;
        CacheDirectiveEntry entry = null;
        try {
          iter = dfs.listCacheDirectives(filter);
          entry = iter.next();
        } catch (IOException e) {
          fail("got IOException while calling " +
              "listCacheDirectives: " + e.getMessage());
        }
        Assert.assertNotNull(entry);
        CacheDirectiveStats stats = entry.getStats();
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
    }, 500, 60000);
  }

};