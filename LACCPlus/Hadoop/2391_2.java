//,temp,ContainerImpl.java,2078,2105,temp,TaskImpl.java,648,672
//,3
public class xxx {
  @Override
  public void handle(TaskEvent event) {
    if (LOG.isDebugEnabled()) {
      LOG.debug("Processing " + event.getTaskID() + " of type "
          + event.getType());
    }
    try {
      writeLock.lock();
      TaskStateInternal oldState = getInternalState();
      try {
        stateMachine.doTransition(event.getType(), event);
      } catch (InvalidStateTransitionException e) {
        LOG.error("Can't handle this event at current state for "
            + this.taskId, e);
        internalError(event.getType());
      }
      if (oldState != getInternalState()) {
        LOG.info(taskId + " Task Transitioned from " + oldState + " to "
            + getInternalState());
      }

    } finally {
      writeLock.unlock();
    }
  }

};