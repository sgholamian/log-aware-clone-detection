//,temp,TaskAttemptImpl.java,2165,2187,temp,TaskAttemptImpl.java,2029,2057
//,3
public class xxx {
  @SuppressWarnings("unchecked")
  private static void notifyTaskAttemptFailed(TaskAttemptImpl taskAttempt) {
    // set the finish time
    taskAttempt.setFinishTime();

    if (taskAttempt.getLaunchTime() != 0) {
      taskAttempt.eventHandler
          .handle(createJobCounterUpdateEventTAFailed(taskAttempt, false));
      TaskAttemptUnsuccessfulCompletionEvent tauce =
          createTaskAttemptUnsuccessfulCompletionEvent(taskAttempt,
              TaskAttemptStateInternal.FAILED);
      taskAttempt.eventHandler.handle(new JobHistoryEvent(
          taskAttempt.attemptId.getTaskId().getJobId(), tauce));
      // taskAttempt.logAttemptFinishedEvent(TaskAttemptStateInternal.FAILED); Not
      // handling failed map/reduce events.
    }else {
      LOG.debug("Not generating HistoryFinish event since start event not " +
          "generated for taskAttempt: " + taskAttempt.getID());
    }
    taskAttempt.eventHandler.handle(new TaskTAttemptEvent(
        taskAttempt.attemptId, TaskEventType.T_ATTEMPT_FAILED));

  }

};