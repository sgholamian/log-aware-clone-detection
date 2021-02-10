//,temp,ComponentInstance.java,423,441,temp,ServiceManager.java,108,127
//,3
public class xxx {
  @Override
  public void handle(ComponentInstanceEvent event) {
    try {
      writeLock.lock();
      ComponentInstanceState oldState = getState();
      try {
        stateMachine.doTransition(event.getType(), event);
      } catch (InvalidStateTransitionException e) {
        LOG.error(getCompInstanceId() + ": Invalid event " + event.getType() +
            " at " + oldState, e);
      }
      if (oldState != getState()) {
        LOG.info(getCompInstanceId() + " Transitioned from " + oldState + " to "
            + getState() + " on " + event.getType() + " event");
      }
    } finally {
      writeLock.unlock();
    }
  }

};