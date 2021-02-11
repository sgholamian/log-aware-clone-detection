//,temp,RMAppImpl.java,750,775,temp,ApplicationImpl.java,446,470
//,3
public class xxx {
  @Override
  public void handle(ApplicationEvent event) {

    this.writeLock.lock();

    try {
      ApplicationId applicationID = event.getApplicationID();
      LOG.debug("Processing " + applicationID + " of type " + event.getType());

      ApplicationState oldState = stateMachine.getCurrentState();
      ApplicationState newState = null;
      try {
        // queue event requesting init of the same app
        newState = stateMachine.doTransition(event.getType(), event);
      } catch (InvalidStateTransitionException e) {
        LOG.warn("Can't handle this event at current state", e);
      }
      if (oldState != newState) {
        LOG.info("Application " + applicationID + " transitioned from "
            + oldState + " to " + newState);
      }
    } finally {
      this.writeLock.unlock();
    }
  }

};