//,temp,TestCacheDirectives.java,833,879,temp,TestCacheDirectives.java,788,818
//,3
public class xxx {
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

};