//,temp,TaskImpl.java,648,672,temp,RMContainerImpl.java,463,488
//,3
public class xxx {
  @Override
  public void handle(RMContainerEvent event) {
    if (LOG.isDebugEnabled()) {
      LOG.debug("Processing " + event.getContainerId() + " of type " + event
              .getType());
    }
    try {
      writeLock.lock();
      RMContainerState oldState = getState();
      try {
         stateMachine.doTransition(event.getType(), event);
      } catch (InvalidStateTransitionException e) {
        LOG.error("Can't handle this event at current state", e);
        LOG.error("Invalid event " + event.getType() + 
            " on container " + this.getContainerId());
      }
      if (oldState != getState()) {
        LOG.info(event.getContainerId() + " Container Transitioned from "
            + oldState + " to " + getState());
      }
    }
    
    finally {
      writeLock.unlock();
    }
  }

};