//,temp,LocalizedResource.java,187,210,temp,RMAppAttemptImpl.java,774,799
//,3
public class xxx {
  @Override
  public void handle(ResourceEvent event) {
    try {
      this.writeLock.lock();

      Path resourcePath = event.getLocalResourceRequest().getPath();
      LOG.debug("Processing " + resourcePath + " of type " + event.getType());

      ResourceState oldState = this.stateMachine.getCurrentState();
      ResourceState newState = null;
      try {
        newState = this.stateMachine.doTransition(event.getType(), event);
      } catch (InvalidStateTransitionException e) {
        LOG.warn("Can't handle this event at current state", e);
      }
      if (oldState != newState) {
        LOG.info("Resource " + resourcePath + (localPath != null ? 
          "(->" + localPath + ")": "") + " transitioned from " + oldState
            + " to " + newState);
      }
    } finally {
      this.writeLock.unlock();
    }
  }

};