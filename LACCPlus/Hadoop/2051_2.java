//,temp,TaskAttemptImpl.java,1958,1994,temp,TaskAttemptImpl.java,1924,1952
//,3
public class xxx {
    @SuppressWarnings("unchecked")
    @Override
    public void transition(TaskAttemptImpl taskAttempt, TaskAttemptEvent event) {
      TaskAttemptTooManyFetchFailureEvent fetchFailureEvent =
          (TaskAttemptTooManyFetchFailureEvent) event;
      // too many fetch failure can only happen for map tasks
      Preconditions
          .checkArgument(taskAttempt.getID().getTaskId().getTaskType() == TaskType.MAP);
      //add to diagnostic
      taskAttempt.addDiagnosticInfo("Too many fetch failures."
          + " Failing the attempt. Last failure reported by " +
          fetchFailureEvent.getReduceId() +
          " from host " + fetchFailureEvent.getReduceHost());

      if (taskAttempt.getLaunchTime() != 0) {
        taskAttempt.eventHandler
            .handle(createJobCounterUpdateEventTAFailed(taskAttempt, true));
        TaskAttemptUnsuccessfulCompletionEvent tauce =
            createTaskAttemptUnsuccessfulCompletionEvent(taskAttempt,
                TaskAttemptStateInternal.FAILED);
        taskAttempt.eventHandler.handle(new JobHistoryEvent(
            taskAttempt.attemptId.getTaskId().getJobId(), tauce));
      }else {
        LOG.debug("Not generating HistoryFinish event since start event not " +
            "generated for taskAttempt: " + taskAttempt.getID());
      }
      taskAttempt.eventHandler.handle(new TaskTAttemptEvent(
          taskAttempt.attemptId, TaskEventType.T_ATTEMPT_FAILED));
    }

};