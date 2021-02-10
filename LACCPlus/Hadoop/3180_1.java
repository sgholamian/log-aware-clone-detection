//,temp,JobImpl.java,997,1029,temp,TaskImpl.java,648,672
//,3
public class xxx {
  @Override
  /**
   * The only entry point to change the Job.
   */
  public void handle(JobEvent event) {
    if (LOG.isDebugEnabled()) {
      LOG.debug("Processing " + event.getJobId() + " of type "
          + event.getType());
    }
    try {
      writeLock.lock();
      JobStateInternal oldState = getInternalState();
      try {
         getStateMachine().doTransition(event.getType(), event);
      } catch (InvalidStateTransitionException e) {
        LOG.error("Can't handle this event at current state", e);
        addDiagnostic("Invalid event " + event.getType() + 
            " on Job " + this.jobId);
        eventHandler.handle(new JobEvent(this.jobId,
            JobEventType.INTERNAL_ERROR));
      }
      //notify the eventhandler of state change
      if (oldState != getInternalState()) {
        LOG.info(jobId + "Job Transitioned from " + oldState + " to "
                 + getInternalState());
        rememberLastNonFinalState(oldState);
      }
    }
    
    finally {
      writeLock.unlock();
    }
  }

};