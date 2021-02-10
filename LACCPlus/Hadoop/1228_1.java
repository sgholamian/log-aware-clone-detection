//,temp,RMAppAttemptImpl.java,774,799,temp,ApplicationImpl.java,446,470
//,3
public class xxx {
  @Override
  public void handle(RMAppAttemptEvent event) {

    this.writeLock.lock();

    try {
      ApplicationAttemptId appAttemptID = event.getApplicationAttemptId();
      LOG.debug("Processing event for " + appAttemptID + " of type "
          + event.getType());
      final RMAppAttemptState oldState = getAppAttemptState();
      try {
        /* keep the master in sync with the state machine */
        this.stateMachine.doTransition(event.getType(), event);
      } catch (InvalidStateTransitionException e) {
        LOG.error("Can't handle this event at current state", e);
        /* TODO fail the application on the failed transition */
      }

      if (oldState != getAppAttemptState()) {
        LOG.info(appAttemptID + " State change from " + oldState + " to "
            + getAppAttemptState());
      }
    } finally {
      this.writeLock.unlock();
    }
  }

};