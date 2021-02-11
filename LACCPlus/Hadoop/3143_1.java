//,temp,RMAppImpl.java,904,937,temp,RMAppAttemptImpl.java,901,934
//,3
public class xxx {
  @Override
  public void handle(RMAppEvent event) {

    this.writeLock.lock();

    try {
      ApplicationId appID = event.getApplicationId();
      LOG.debug("Processing event for " + appID + " of type "
          + event.getType());
      final RMAppState oldState = getState();
      try {
        /* keep the master in sync with the state machine */
        this.stateMachine.doTransition(event.getType(), event);
      } catch (InvalidStateTransitionException e) {
        LOG.error("App: " + appID
            + " can't handle this event at current state", e);
        onInvalidStateTransition(event.getType(), oldState);
      }

      // Log at INFO if we're not recovering or not in a terminal state.
      // Log at DEBUG otherwise.
      if ((oldState != getState()) &&
          (((recoveredFinalState == null)) ||
            (event.getType() != RMAppEventType.RECOVER))) {
        LOG.info(String.format(STATE_CHANGE_MESSAGE, appID, oldState,
            getState(), event.getType()));
      } else if ((oldState != getState()) && LOG.isDebugEnabled()) {
        LOG.debug(String.format(STATE_CHANGE_MESSAGE, appID, oldState,
            getState(), event.getType()));
      }
    } finally {
      this.writeLock.unlock();
    }
  }

};