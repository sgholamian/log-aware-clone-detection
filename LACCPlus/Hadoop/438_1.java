//,temp,ActiveStandbyElector.java,469,526,temp,ActiveStandbyElector.java,409,464
//,3
public class xxx {
  @Override
  public synchronized void processResult(int rc, String path, Object ctx,
      Stat stat) {
    if (isStaleClient(ctx)) return;
    monitorLockNodePending = false;

    assert wantToBeInElection :
        "Got a StatNode result after quitting election";

    if (LOG.isDebugEnabled()) {
      LOG.debug("StatNode result: " + rc + " for path: " + path
          + " connectionState: " + zkConnectionState + " for " + this);
    }

    Code code = Code.get(rc);
    if (isSuccess(code)) {
      // the following owner check completes verification in case the lock znode
      // creation was retried
      if (stat.getEphemeralOwner() == zkClient.getSessionId()) {
        // we own the lock znode. so we are the leader
        if (!becomeActive()) {
          reJoinElectionAfterFailureToBecomeActive();
        }
      } else {
        // we dont own the lock znode. so we are a standby.
        becomeStandby();
      }
      // the watch set by us will notify about changes
      return;
    }

    if (isNodeDoesNotExist(code)) {
      // the lock znode disappeared before we started monitoring it
      enterNeutralMode();
      joinElectionInternal();
      return;
    }

    String errorMessage = "Received stat error from Zookeeper. code:"
        + code.toString();
    LOG.debug(errorMessage);

    if (shouldRetry(code)) {
      if (statRetryCount < maxRetryNum) {
        ++statRetryCount;
        monitorLockNodeAsync();
        return;
      }
      errorMessage = errorMessage
          + ". Not retrying further znode monitoring connection errors.";
    } else if (isSessionExpired(code)) {
      // This isn't fatal - the client Watcher will re-join the election
      LOG.warn("Lock monitoring failed because session was lost");
      return;
    }

    fatalError(errorMessage);
  }

};