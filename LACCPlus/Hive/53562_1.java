//,temp,HDFSCleanup.java,93,127,temp,ZooKeeperCleanup.java,88,127
//,3
public class xxx {
  public void run() {
    while (!stop) {
      try {
        // Put each check in a separate try/catch, so if that particular
        // cycle fails, it'll try again on the next cycle.
        FileSystem fs=null;
        try {
          fs = new Path(storage_root).getFileSystem(appConf);
          checkFiles(fs);
        } catch (Exception e) {
          LOG.error("Cleanup cycle failed: " + e.getMessage());
        } finally {
          if(fs != null) {
            try {
              fs.close();
            }
            catch (Exception e) {
              LOG.error("Closing file system failed: " + e.getMessage());
            }
          }
        }

        long sleepMillis = (long) (Math.random() * interval);
        LOG.info("Next execution: " + new Date(new Date().getTime()
                             + sleepMillis));
        Thread.sleep(sleepMillis);

      } catch (Exception e) {
        // If sleep fails, we should exit now before things get worse.
        isRunning = false;
        LOG.error("Cleanup failed: " + e.getMessage(), e);
      }
    }
    isRunning = false;
  }

};