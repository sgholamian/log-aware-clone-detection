//,temp,FSNamesystem.java,4093,4118,temp,FSNamesystem.java,4004,4027
//,3
public class xxx {
    @Override
    public void run() {
      while (fsRunning && shouldRun) {
        try {
          if (!isInSafeMode()) {
            clearCorruptLazyPersistFiles();
          } else {
            if (FSNamesystem.LOG.isDebugEnabled()) {
              FSNamesystem.LOG
                  .debug("Namenode is in safemode, skipping scrubbing of corrupted lazy-persist files.");
            }
          }
        } catch (Exception e) {
          FSNamesystem.LOG.error(
              "Ignoring exception in LazyPersistFileScrubber:", e);
        }

        try {
          Thread.sleep(scrubIntervalSec * 1000);
        } catch (InterruptedException e) {
          FSNamesystem.LOG.info(
              "LazyPersistFileScrubber was interrupted, exiting");
          break;
        }
      }
    }

};