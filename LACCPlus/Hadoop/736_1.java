//,temp,RMStateStore.java,977,999,temp,ApplicationImpl.java,446,470
//,3
public class xxx {
  protected void handleStoreEvent(RMStateStoreEvent event) {
    this.writeLock.lock();
    try {

      if (LOG.isDebugEnabled()) {
        LOG.debug("Processing event of type " + event.getType());
      }

      final RMStateStoreState oldState = getRMStateStoreState();

      this.stateMachine.doTransition(event.getType(), event);

      if (oldState != getRMStateStoreState()) {
        LOG.info("RMStateStore state change from " + oldState + " to "
            + getRMStateStoreState());
      }

    } catch (InvalidStateTransitionException e) {
      LOG.error("Can't handle this event at current state", e);
    } finally {
      this.writeLock.unlock();
    }
  }

};