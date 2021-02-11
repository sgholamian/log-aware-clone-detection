//,temp,ComponentInstance.java,423,441,temp,ServiceManager.java,108,127
//,3
public class xxx {
  @Override
  public void handle(ServiceEvent event) {
    try {
      writeLock.lock();
      State oldState = getState();
      try {
        stateMachine.doTransition(event.getType(), event);
      } catch (InvalidStateTransitionException e) {
        LOG.error(MessageFormat.format(
            "[SERVICE]: Invalid event {1} at {2}.", event.getType(),
            oldState), e);
      }
      if (oldState != getState()) {
        LOG.info("[SERVICE] Transitioned from {} to {} on {} event.",
            oldState, getState(), event.getType());
      }
    } finally {
      writeLock.unlock();
    }
  }

};