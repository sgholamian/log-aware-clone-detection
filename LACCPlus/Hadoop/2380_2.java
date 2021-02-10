//,temp,TimelineEntityConverterV2.java,176,205,temp,TimelineEntityConverterV2.java,141,161
//,3
public class xxx {
  private TimelineEntity createTaskEntity(TaskInfo taskInfo) {
    TimelineEntity task = new TimelineEntity();
    task.setType(TASK);
    task.setId(taskInfo.getTaskId().toString());
    task.setCreatedTime(taskInfo.getStartTime());

    task.addInfo("START_TIME", taskInfo.getStartTime());
    task.addInfo("FINISH_TIME", taskInfo.getFinishTime());
    task.addInfo("TASK_TYPE", taskInfo.getTaskType());
    task.addInfo("TASK_STATUS", taskInfo.getTaskStatus());
    task.addInfo("ERROR_INFO", taskInfo.getError());

    // add metrics from counters
    Counters counters = taskInfo.getCounters();
    if (counters != null) {
      addMetrics(task, counters);
    }
    LOG.info("converted task " + taskInfo.getTaskId() +
        " to a timeline entity");
    return task;
  }

};