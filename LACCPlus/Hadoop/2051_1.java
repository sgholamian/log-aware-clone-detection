//,temp,TaskAttemptImpl.java,1958,1994,temp,TaskAttemptImpl.java,1924,1952
//,3
public class xxx {
    @SuppressWarnings("unchecked")
    @Override
    public TaskAttemptStateInternal transition(TaskAttemptImpl taskAttempt, 
        TaskAttemptEvent event) {
      if(taskAttempt.getID().getTaskId().getTaskType() == TaskType.REDUCE) {
        // after a reduce task has succeeded, its outputs are in safe in HDFS.
        // logically such a task should not be killed. we only come here when
        // there is a race condition in the event queue. E.g. some logic sends
        // a kill request to this attempt when the successful completion event
        // for this task is already in the event queue. so the kill event will
        // get executed immediately after the attempt is marked successful and 
        // result in this transition being exercised.
        // ignore this for reduce tasks
        LOG.info("Ignoring killed event for successful reduce task attempt" +
                  taskAttempt.getID().toString());
        return TaskAttemptStateInternal.SUCCEEDED;
      }
      if(event instanceof TaskAttemptKillEvent) {
        TaskAttemptKillEvent msgEvent = (TaskAttemptKillEvent) event;
        //add to diagnostic
        taskAttempt.addDiagnosticInfo(msgEvent.getMessage());
      }

      // not setting a finish time since it was set on success
      assert (taskAttempt.getFinishTime() != 0);

      assert (taskAttempt.getLaunchTime() != 0);
      taskAttempt.eventHandler
          .handle(createJobCounterUpdateEventTAKilled(taskAttempt, true));
      TaskAttemptUnsuccessfulCompletionEvent tauce = createTaskAttemptUnsuccessfulCompletionEvent(
          taskAttempt, TaskAttemptStateInternal.KILLED);
      taskAttempt.eventHandler.handle(new JobHistoryEvent(taskAttempt.attemptId
          .getTaskId().getJobId(), tauce));
      taskAttempt.eventHandler.handle(new TaskTAttemptEvent(
          taskAttempt.attemptId, TaskEventType.T_ATTEMPT_KILLED));
      return TaskAttemptStateInternal.KILLED;
    }

};