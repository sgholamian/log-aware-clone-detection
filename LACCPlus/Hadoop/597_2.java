//,temp,ZKFailoverController.java,628,688,temp,ZKFailoverController_before_log_fix.java,627,687
//,2
public class xxx {
  private void doGracefulFailover()
      throws ServiceFailedException, IOException, InterruptedException {
    int timeout = FailoverController.getGracefulFenceTimeout(conf) * 2;
    
    // Phase 1: pre-flight checks
    checkEligibleForFailover();
    
    // Phase 2: determine old/current active node. Check that we're not
    // ourselves active, etc.
    HAServiceTarget oldActive = getCurrentActive();
    if (oldActive == null) {
      // No node is currently active. So, if we aren't already
      // active ourselves by means of a normal election, then there's
      // probably something preventing us from becoming active.
      throw new ServiceFailedException(
          "No other node is currently active.");
    }
    
    if (oldActive.getAddress().equals(localTarget.getAddress())) {
      LOG.info("Local node " + localTarget + " is already active. " +
          "No need to failover. Returning success.");
      return;
    }
    
    // Phase 3: ask the old active to yield from the election.
    LOG.info("Asking " + oldActive + " to cede its active state for " +
        timeout + "ms");
    ZKFCProtocol oldZkfc = oldActive.getZKFCProxy(conf, timeout);
    oldZkfc.cedeActive(timeout);

    // Phase 4: wait for the normal election to make the local node
    // active.
    ActiveAttemptRecord attempt = waitForActiveAttempt(timeout + 60000);
    
    if (attempt == null) {
      // We didn't even make an attempt to become active.
      synchronized(this) {
        if (lastHealthState != State.SERVICE_HEALTHY) {
          throw new ServiceFailedException("Unable to become active. " +
            "Service became unhealthy while trying to failover.");          
        }
      }
      
      throw new ServiceFailedException("Unable to become active. " +
          "Local node did not get an opportunity to do so from ZooKeeper, " +
          "or the local node took too long to transition to active.");
    }

    // Phase 5. At this point, we made some attempt to become active. So we
    // can tell the old active to rejoin if it wants. This allows a quick
    // fail-back if we immediately crash.
    oldZkfc.cedeActive(-1);
    
    if (attempt.succeeded) {
      LOG.info("Successfully became active. " + attempt.status);
    } else {
      // Propagate failure
      String msg = "Failed to become active. " + attempt.status;
      throw new ServiceFailedException(msg);
    }
  }

};