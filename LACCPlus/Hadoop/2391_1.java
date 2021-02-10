//,temp,ContainerImpl.java,2078,2105,temp,TaskImpl.java,648,672
//,3
public class xxx {
  @Override
  public void handle(ContainerEvent event) {
    try {
      this.writeLock.lock();

      ContainerId containerID = event.getContainerID();
      if (LOG.isDebugEnabled()) {
        LOG.debug("Processing " + containerID + " of type " + event.getType());
      }
      ContainerState oldState = stateMachine.getCurrentState();
      ContainerState newState = null;
      try {
        newState =
            stateMachine.doTransition(event.getType(), event);
      } catch (InvalidStateTransitionException e) {
        LOG.warn("Can't handle this event at current state: Current: ["
            + oldState + "], eventType: [" + event.getType() + "]," +
            " container: [" + containerID + "]", e);
      }
      if (newState != null && oldState != newState) {
        LOG.info("Container " + containerID + " transitioned from "
            + oldState
            + " to " + newState);
      }
    } finally {
      this.writeLock.unlock();
    }
  }

};