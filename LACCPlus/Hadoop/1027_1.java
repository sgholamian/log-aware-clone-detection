//,temp,TaskImpl.java,1141,1180,temp,TaskImpl.java,1100,1130
//,3
public class xxx {
    @Override
    public TaskStateInternal transition(TaskImpl task, TaskEvent event) {
      TaskAttemptId attemptId = null;
      if (event instanceof TaskTAttemptEvent) {
        TaskTAttemptEvent castEvent = (TaskTAttemptEvent) event;
        attemptId = castEvent.getTaskAttemptID(); 
        if (task.getInternalState() == TaskStateInternal.SUCCEEDED &&
            !attemptId.equals(task.successfulAttempt)) {
          // don't allow a different task attempt to override a previous
          // succeeded state
          task.finishedAttempts.add(castEvent.getTaskAttemptID());
          task.inProgressAttempts.remove(castEvent.getTaskAttemptID());
          return TaskStateInternal.SUCCEEDED;
        }
      }

      // a successful REDUCE task should not be overridden
      // TODO: consider moving it to MapTaskImpl
      if (!TaskType.MAP.equals(task.getType())) {
        LOG.error("Unexpected event for REDUCE task " + event.getType());
        task.internalError(event.getType());
      }

      // successful attempt is now killed. reschedule
      // tell the job about the rescheduling
      unSucceed(task);
      task.handleTaskAttemptCompletion(attemptId,
          TaskAttemptCompletionEventStatus.KILLED);
      task.eventHandler.handle(new JobMapTaskRescheduledEvent(task.taskId));
      // typically we are here because this map task was run on a bad node and
      // we want to reschedule it on a different node.
      // Depending on whether there are previous failed attempts or not this
      // can SCHEDULE or RESCHEDULE the container allocate request. If this
      // SCHEDULE's then the dataLocal hosts of this taskAttempt will be used
      // from the map splitInfo. So the bad node might be sent as a location
      // to the RM. But the RM would ignore that just like it would ignore
      // currently pending container requests affinitized to bad nodes.
      task.addAndScheduleAttempt(Avataar.VIRGIN);
      return TaskStateInternal.SCHEDULED;
    }

};