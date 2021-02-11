//,temp,ZKFailoverController.java,808,841,temp,ZKFailoverController.java,560,593
//,3
public class xxx {
  void verifyChangedServiceState(HAServiceState changedState) {
    synchronized (elector) {
      synchronized (this) {
        if (serviceState == HAServiceState.INITIALIZING) {
          if (quitElectionOnBadState) {
            LOG.debug("rechecking for electability from bad state");
            recheckElectability();
          }
          return;
        }
        if (changedState == serviceState) {
          serviceStateMismatchCount = 0;
          return;
        }
        if (serviceStateMismatchCount == 0) {
          // recheck one more time. As this might be due to parallel transition.
          serviceStateMismatchCount++;
          return;
        }
        // quit the election as the expected state and reported state
        // mismatches.
        LOG.error("Local service " + localTarget
            + " has changed the serviceState to " + changedState
            + ". Expected was " + serviceState
            + ". Quitting election marking fencing necessary.");
        delayJoiningUntilNanotime = System.nanoTime()
            + TimeUnit.MILLISECONDS.toNanos(1000);
        elector.quitElection(true);
        quitElectionOnBadState = true;
        serviceStateMismatchCount = 0;
        serviceState = HAServiceState.INITIALIZING;
      }
    }
  }

};