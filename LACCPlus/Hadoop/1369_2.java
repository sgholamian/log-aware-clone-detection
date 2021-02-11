//,temp,RMAppImpl.java,750,775,temp,RMNodeImpl.java,446,467
//,3
public class xxx {
  public void handle(RMNodeEvent event) {
    LOG.debug("Processing " + event.getNodeId() + " of type " + event.getType());
    try {
      writeLock.lock();
      NodeState oldState = getState();
      try {
         stateMachine.doTransition(event.getType(), event);
      } catch (InvalidStateTransitionException e) {
        LOG.error("Can't handle this event at current state", e);
        LOG.error("Invalid event " + event.getType() + 
            " on Node  " + this.nodeId);
      }
      if (oldState != getState()) {
        LOG.info(nodeId + " Node Transitioned from " + oldState + " to "
                 + getState());
      }
    }
    
    finally {
      writeLock.unlock();
    }
  }

};