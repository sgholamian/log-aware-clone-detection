//,temp,ActiveStandbyElector.java,469,526,temp,ActiveStandbyElector.java,409,464
//,3
public class xxx {
  @Override
  public synchronized void processResult(int rc, String path, Object ctx,
      String name) {
    if (isStaleClient(ctx)) return;
    if (LOG.isDebugEnabled()) {
      LOG.debug("CreateNode result: " + rc + " for path: " + path
          + " connectionState: " + zkConnectionState +
          "  for " + this);
    }

    Code code = Code.get(rc);
    if (isSuccess(code)) {
      // we successfully created the znode. we are the leader. start monitoring
      if (becomeActive()) {
        monitorActiveStatus();
      } else {
        reJoinElectionAfterFailureToBecomeActive();
      }
      return;
    }

    if (isNodeExists(code)) {
      if (createRetryCount == 0) {
        // znode exists and we did not retry the operation. so a different
        // instance has created it. become standby and monitor lock.
        becomeStandby();
      }
      // if we had retried then the znode could have been created by our first
      // attempt to the server (that we lost) and this node exists response is
      // for the second attempt. verify this case via ephemeral node owner. this
      // will happen on the callback for monitoring the lock.
      monitorActiveStatus();
      return;
    }

    String errorMessage = "Received create error from Zookeeper. code:"
        + code.toString() + " for path " + path;
    LOG.debug(errorMessage);

    if (shouldRetry(code)) {
      if (createRetryCount < maxRetryNum) {
        LOG.debug("Retrying createNode createRetryCount: " + createRetryCount);
        ++createRetryCount;
        createLockNodeAsync();
        return;
      }
      errorMessage = errorMessage
          + ". Not retrying further znode create connection errors.";
    } else if (isSessionExpired(code)) {
      // This isn't fatal - the client Watcher will re-join the election
      LOG.warn("Lock acquisition failed because session was lost");
      return;
    }

    fatalError(errorMessage);
  }

};