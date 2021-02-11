//,temp,LeaseManager.java,68,85,temp,DatanodeStateMachine.java,389,401
//,3
public class xxx {
  public void start() {
    LOG.debug("Starting {} LeaseManager service", name);
    activeLeases = new ConcurrentHashMap<>();
    leaseMonitor = new LeaseMonitor();
    leaseMonitorThread = new Thread(leaseMonitor);
    leaseMonitorThread.setName(name + "-LeaseManager#LeaseMonitor");
    leaseMonitorThread.setDaemon(true);
    leaseMonitorThread.setUncaughtExceptionHandler((thread, throwable) -> {
      // Let us just restart this thread after logging an error.
      // if this thread is not running we cannot handle Lease expiry.
      LOG.error("LeaseMonitor thread encountered an error. Thread: {}",
          thread.toString(), throwable);
      leaseMonitorThread.start();
    });
    LOG.debug("Starting {}-LeaseManager#LeaseMonitor Thread", name);
    leaseMonitorThread.start();
    isRunning = true;
  }

};