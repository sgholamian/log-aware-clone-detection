//,temp,BlockPoolSlice.java,223,258,temp,VolumeUsage.java,109,144
//,3
public class xxx {
  long loadScmUsed() {
    long cachedScmUsed;
    long mtime;
    Scanner sc;

    try {
      sc = new Scanner(scmUsedFile, "UTF-8");
    } catch (FileNotFoundException fnfe) {
      return -1;
    }

    try {
      // Get the recorded scmUsed from the file.
      if (sc.hasNextLong()) {
        cachedScmUsed = sc.nextLong();
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
      if (mtime > 0 && (Time.now() - mtime < 600000L)) {
        LOG.info("Cached ScmUsed found for {} : {} ", rootDir,
            cachedScmUsed);
        return cachedScmUsed;
      }
      return -1;
    } finally {
      sc.close();
    }
  }

};