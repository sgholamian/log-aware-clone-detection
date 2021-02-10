//,temp,TimelineEntityConverterV2.java,176,205,temp,TimelineEntityConverterV2.java,141,161
//,3
public class xxx {
  private TimelineEntity createTaskAttemptEntity(
      TaskAttemptInfo taskAttemptInfo) {
    TimelineEntity taskAttempt = new TimelineEntity();
    taskAttempt.setType(TASK_ATTEMPT);
    taskAttempt.setId(taskAttemptInfo.getAttemptId().toString());
    taskAttempt.setCreatedTime(taskAttemptInfo.getStartTime());

    taskAttempt.addInfo("START_TIME", taskAttemptInfo.getStartTime());
    taskAttempt.addInfo("FINISH_TIME", taskAttemptInfo.getFinishTime());
    taskAttempt.addInfo("MAP_FINISH_TIME",
        taskAttemptInfo.getMapFinishTime());
    taskAttempt.addInfo("SHUFFLE_FINISH_TIME",
        taskAttemptInfo.getShuffleFinishTime());
    taskAttempt.addInfo("SORT_FINISH_TIME",
        taskAttemptInfo.getSortFinishTime());
    taskAttempt.addInfo("TASK_STATUS", taskAttemptInfo.getTaskStatus());
    taskAttempt.addInfo("STATE", taskAttemptInfo.getState());
    taskAttempt.addInfo("ERROR", taskAttemptInfo.getError());
    taskAttempt.addInfo("CONTAINER_ID",
        taskAttemptInfo.getContainerId().toString());

    // add metrics from counters
    Counters counters = taskAttemptInfo.getCounters();
    if (counters != null) {
      addMetrics(taskAttempt, counters);
    }
    LOG.info("converted task attempt " + taskAttemptInfo.getAttemptId() +
        " to a timeline entity");
    return taskAttempt;
  }

};