//,temp,TaskImpl.java,1141,1180,temp,TaskImpl.java,1100,1130
//,3
public class xxx {
    @Override
    public TaskStateInternal transition(TaskImpl task, TaskEvent event) {
      TaskTAttemptEvent castEvent = (TaskTAttemptEvent) event;
      if (task.getInternalState() == TaskStateInternal.SUCCEEDED &&
          !castEvent.getTaskAttemptID().equals(task.successfulAttempt)) {
        // don't allow a different task attempt to override a previous
        // succeeded state
        task.finishedAttempts.add(castEvent.getTaskAttemptID());
        task.inProgressAttempts.remove(castEvent.getTaskAttemptID());
        return TaskStateInternal.SUCCEEDED;
      }

      // a successful REDUCE task should not be overridden
      //TODO: consider moving it to MapTaskImpl
      if (!TaskType.MAP.equals(task.getType())) {
        LOG.error("Unexpected event for REDUCE task " + event.getType());
        task.internalError(event.getType());
      }
      
      // tell the job about the rescheduling
      task.eventHandler.handle(
          new JobMapTaskRescheduledEvent(task.taskId));
      // super.transition is mostly coded for the case where an
      //  UNcompleted task failed.  When a COMPLETED task retroactively
      //  fails, we have to let AttemptFailedTransition.transition
      //  believe that there's no redundancy.
      unSucceed(task);
      // fake increase in Uncomplete attempts for super.transition
      task.inProgressAttempts.add(castEvent.getTaskAttemptID());
      return super.transition(task, event);
    }

};