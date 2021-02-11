//,temp,TimelineEntityConverterV1.java,139,163,temp,TimelineEntityConverterV1.java,109,124
//,3
public class xxx {
  private TimelineEntity
      createTaskAttemptEntity(TaskAttemptInfo taskAttemptInfo) {
    TimelineEntity taskAttempt = new TimelineEntity();
    taskAttempt.setEntityType(TASK_ATTEMPT);
    taskAttempt.setEntityId(taskAttemptInfo.getAttemptId().toString());
    taskAttempt.setStartTime(taskAttemptInfo.getStartTime());

    taskAttempt.addOtherInfo("START_TIME", taskAttemptInfo.getStartTime());
    taskAttempt.addOtherInfo("FINISH_TIME", taskAttemptInfo.getFinishTime());
    taskAttempt.addOtherInfo("MAP_FINISH_TIME",
        taskAttemptInfo.getMapFinishTime());
    taskAttempt.addOtherInfo("SHUFFLE_FINISH_TIME",
        taskAttemptInfo.getShuffleFinishTime());
    taskAttempt.addOtherInfo("SORT_FINISH_TIME",
        taskAttemptInfo.getSortFinishTime());
    taskAttempt.addOtherInfo("TASK_STATUS", taskAttemptInfo.getTaskStatus());
    taskAttempt.addOtherInfo("STATE", taskAttemptInfo.getState());
    taskAttempt.addOtherInfo("ERROR", taskAttemptInfo.getError());
    taskAttempt.addOtherInfo("CONTAINER_ID",
        taskAttemptInfo.getContainerId().toString());

    LOG.info("converted task attempt " + taskAttemptInfo.getAttemptId() +
        " to a timeline entity");
    return taskAttempt;
  }

};