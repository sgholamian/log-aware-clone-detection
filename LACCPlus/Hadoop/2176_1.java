//,temp,BlockPoolSlice.java,223,258,temp,VolumeUsage.java,109,144
//,3
public class xxx {
  long loadDfsUsed() {
    long cachedDfsUsed;
    long mtime;
    Scanner sc;

    try {
      sc = new Scanner(new File(currentDir, DU_CACHE_FILE), "UTF-8");
    } catch (FileNotFoundException fnfe) {
      return -1;
    }

    try {
      // Get the recorded dfsUsed from the file.
      if (sc.hasNextLong()) {
        cachedDfsUsed = sc.nextLong();
      } else {
        return -1;
      }
      // Get the recorded mtime from the file.
      if (sc.hasNextLong()) {
        mtime = sc.nextLong();
      } else {
        return -1;
      }

      // Return the cached value if mtime is okay.
      if (mtime > 0 && (timer.now() - mtime < cachedDfsUsedCheckTime)) {
        FsDatasetImpl.LOG.info("Cached dfsUsed found for " + currentDir + ": " +
            cachedDfsUsed);
        return cachedDfsUsed;
      }
      return -1;
    } finally {
      sc.close();
    }
  }

};