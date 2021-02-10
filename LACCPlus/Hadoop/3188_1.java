//,temp,LocalizedResource.java,188,214,temp,ContainerImpl.java,2078,2105
//,3
public class xxx {
  @Override
  public void handle(ResourceEvent event) {
    try {
      this.writeLock.lock();

      Path resourcePath = event.getLocalResourceRequest().getPath();
      if (LOG.isDebugEnabled()) {
        LOG.debug("Processing " + resourcePath + " of type " + event.getType());
      }
      ResourceState oldState = this.stateMachine.getCurrentState();
      ResourceState newState = null;
      try {
        newState = this.stateMachine.doTransition(event.getType(), event);
      } catch (InvalidStateTransitionException e) {
        LOG.warn("Can't handle this event at current state", e);
      }
      if (newState != null && oldState != newState) {
        if (LOG.isDebugEnabled()) {
          LOG.debug("Resource " + resourcePath + (localPath != null ?
              "(->" + localPath + ")": "") + " size : " + getSize()
              + " transitioned from " + oldState + " to " + newState);
        }
      }
    } finally {
      this.writeLock.unlock();
    }
  }

};