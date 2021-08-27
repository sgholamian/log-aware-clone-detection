//,temp,HDFSCleanup.java,93,127,temp,ZooKeeperCleanup.java,88,127
//,3
public class xxx {
  public void run() {
    CuratorFramework zk = null;
    List<String> nodes = null;
    isRunning = true;
    while (!stop) {
      try {
        // Put each check in a separate try/catch, so if that particular
        // cycle fails, it'll try again on the next cycle.
        try {
          zk = ZooKeeperStorage.zkOpen(appConf);

          nodes = getChildList(zk);

          for (String node : nodes) {
            boolean deleted = checkAndDelete(node, zk);
            if (!deleted) {
              break;
            }
          }

          zk.close();
        } catch (Exception e) {
          LOG.error("Cleanup cycle failed: " + e.getMessage());
        } finally {
          if (zk != null) zk.close();
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