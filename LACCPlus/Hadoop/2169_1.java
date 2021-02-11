//,temp,ZKFailoverController.java,735,784,temp,ZKFailoverController_before_log_fix.java,734,783
//,2
public class xxx {
  private void recheckElectability() {
    // Maintain lock ordering of elector -> ZKFC
    synchronized (elector) {
      synchronized (this) {
        boolean healthy = lastHealthState == State.SERVICE_HEALTHY;
    
        long remainingDelay = delayJoiningUntilNanotime - System.nanoTime(); 
        if (remainingDelay > 0) {
          if (healthy) {
            LOG.info("Would have joined master election, but this node is " +
                "prohibited from doing so for " +
                TimeUnit.NANOSECONDS.toMillis(remainingDelay) + " more ms");
          }
          scheduleRecheck(remainingDelay);
          return;
        }
    
        switch (lastHealthState) {
        case SERVICE_HEALTHY:
          elector.joinElection(targetToData(localTarget));
          if (quitElectionOnBadState) {
            quitElectionOnBadState = false;
          }
          break;
          
        case INITIALIZING:
          LOG.info("Ensuring that " + localTarget + " does not " +
              "participate in active master election");
          elector.quitElection(false);
          serviceState = HAServiceState.INITIALIZING;
          break;
    
        case SERVICE_UNHEALTHY:
        case SERVICE_NOT_RESPONDING:
          LOG.info("Quitting master election for " + localTarget +
              " and marking that fencing is necessary");
          elector.quitElection(true);
          serviceState = HAServiceState.INITIALIZING;
          break;
          
        case HEALTH_MONITOR_FAILED:
          fatalError("Health monitor failed!");
          break;
          
        default:
          throw new IllegalArgumentException("Unhandled state:" + lastHealthState);
        }
      }
    }
  }

};