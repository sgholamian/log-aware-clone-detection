//,temp,TaskAttemptImpl.java,2165,2187,temp,TaskAttemptImpl.java,2029,2057
//,3
public class xxx {
    @SuppressWarnings("unchecked")
    @Override
    public void transition(TaskAttemptImpl taskAttempt,
        TaskAttemptEvent event) {
      //set the finish time
      taskAttempt.setFinishTime();
      if (taskAttempt.getLaunchTime() != 0) {
        taskAttempt.eventHandler
            .handle(createJobCounterUpdateEventTAKilled(taskAttempt, false));
        TaskAttemptUnsuccessfulCompletionEvent tauce =
            createTaskAttemptUnsuccessfulCompletionEvent(taskAttempt,
                TaskAttemptStateInternal.KILLED);
        taskAttempt.eventHandler.handle(new JobHistoryEvent(
            taskAttempt.attemptId.getTaskId().getJobId(), tauce));
      }else {
        LOG.debug("Not generating HistoryFinish event since start event not " +
            "generated for taskAttempt: " + taskAttempt.getID());
      }

      if (event instanceof TaskAttemptKillEvent) {
        taskAttempt.addDiagnosticInfo(
            ((TaskAttemptKillEvent) event).getMessage());
      }

//      taskAttempt.logAttemptFinishedEvent(TaskAttemptStateInternal.KILLED); Not logging Map/Reduce attempts in case of failure.
      taskAttempt.eventHandler.handle(new TaskTAttemptEvent(
          taskAttempt.attemptId,
          TaskEventType.T_ATTEMPT_KILLED));
    }

};