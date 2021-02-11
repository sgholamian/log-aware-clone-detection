//,temp,JobImpl.java,983,1015,temp,TaskAttemptImpl.java,1186,1215
//,3
public class xxx {
  @SuppressWarnings("unchecked")
  @Override
  public void handle(TaskAttemptEvent event) {
    if (LOG.isDebugEnabled()) {
      LOG.debug("Processing " + event.getTaskAttemptID() + " of type "
          + event.getType());
    }
    writeLock.lock();
    try {
      final TaskAttemptStateInternal oldState = getInternalState()  ;
      try {
        stateMachine.doTransition(event.getType(), event);
      } catch (InvalidStateTransitionException e) {
        LOG.error("Can't handle this event at current state for "
            + this.attemptId, e);
        eventHandler.handle(new JobDiagnosticsUpdateEvent(
            this.attemptId.getTaskId().getJobId(), "Invalid event " + event.getType() + 
            " on TaskAttempt " + this.attemptId));
        eventHandler.handle(new JobEvent(this.attemptId.getTaskId().getJobId(),
            JobEventType.INTERNAL_ERROR));
      }
      if (oldState != getInternalState()) {
          LOG.info(attemptId + " TaskAttempt Transitioned from " 
           + oldState + " to "
           + getInternalState());
      }
    } finally {
      writeLock.unlock();
    }
  }

};