//,temp,PlanQueue.java,128,144,temp,InMemorySCMStore.java,483,518
//,3
public class xxx {
    @Override
    public void run() {
      try {
        LOG.info("Checking the initial app list for finished applications.");
        synchronized (initialAppsLock) {
          if (initialApps.isEmpty()) {
            // we're fine, no-op; there are no active apps that were running at
            // the time of the service start
          } else {
            LOG.info("Looking into " + initialApps.size()
                + " apps to see if they are still active");
            Iterator<ApplicationId> it = initialApps.iterator();
            while (it.hasNext()) {
              ApplicationId id = it.next();
              try {
                if (!taskAppChecker.isApplicationActive(id)) {
                  // remove it from the list
                  it.remove();
                }
              } catch (YarnException e) {
                LOG.warn("Exception while checking the app status;"
                    + " will leave the entry in the list", e);
                // continue
              }
            }
          }
          LOG.info("There are now " + initialApps.size()
              + " entries in the list");
        }
      } catch (Throwable e) {
        LOG.error(
            "Unexpected exception thrown during in-memory store app check task."
                + " Rescheduling task.", e);
      }

    }

};