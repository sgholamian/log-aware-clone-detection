//,temp,ZKFailoverController.java,560,593,temp,ZKFailoverController_before_log_fix.java,807,840
//,3
public class xxx {
  private void doCedeActive(int millisToCede) 
      throws AccessControlException, ServiceFailedException, IOException {
    int timeout = FailoverController.getGracefulFenceTimeout(conf);

    // Lock elector to maintain lock ordering of elector -> ZKFC
    synchronized (elector) {
      synchronized (this) {
        if (millisToCede <= 0) {
          delayJoiningUntilNanotime = 0;
          recheckElectability();
          return;
        }
  
        LOG.info("Requested by " + UserGroupInformation.getCurrentUser() +
            " at " + Server.getRemoteAddress() + " to cede active role.");
        boolean needFence = false;
        try {
          localTarget.getProxy(conf, timeout).transitionToStandby(createReqInfo());
          LOG.info("Successfully ensured local node is in standby mode");
        } catch (IOException ioe) {
          LOG.warn("Unable to transition local node to standby: " +
              ioe.getLocalizedMessage());
          LOG.warn("Quitting election but indicating that fencing is " +
              "necessary");
          needFence = true;
        }
        delayJoiningUntilNanotime = System.nanoTime() +
            TimeUnit.MILLISECONDS.toNanos(millisToCede);
        elector.quitElection(needFence);
        serviceState = HAServiceState.INITIALIZING;
      }
    }
    recheckElectability();
  }

};