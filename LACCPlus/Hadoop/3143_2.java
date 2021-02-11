//,temp,RMAppImpl.java,904,937,temp,RMAppAttemptImpl.java,901,934
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
        LOG.error("App attempt: " + appAttemptID
            + " can't handle this event at current state", e);
        onInvalidTranstion(event.getType(), oldState);
      }

      // Log at INFO if we're not recovering or not in a terminal state.
      // Log at DEBUG otherwise.
      if ((oldState != getAppAttemptState()) &&
          ((recoveredFinalState == null) ||
            (event.getType() != RMAppAttemptEventType.RECOVER))) {
        LOG.info(String.format(STATE_CHANGE_MESSAGE, appAttemptID, oldState,
            getAppAttemptState(), event.getType()));
      } else if ((oldState != getAppAttemptState()) && LOG.isDebugEnabled()) {
        LOG.debug(String.format(STATE_CHANGE_MESSAGE, appAttemptID, oldState,
            getAppAttemptState(), event.getType()));
      }
    } finally {
      this.writeLock.unlock();
    }
  }

};