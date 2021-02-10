//,temp,TimelineEntityConverterV1.java,139,163,temp,TimelineEntityConverterV1.java,109,124
//,3
public class xxx {
  private TimelineEntity createTaskEntity(TaskInfo taskInfo) {
    TimelineEntity task = new TimelineEntity();
    task.setEntityType(TASK);
    task.setEntityId(taskInfo.getTaskId().toString());
    task.setStartTime(taskInfo.getStartTime());

    task.addOtherInfo("START_TIME", taskInfo.getStartTime());
    task.addOtherInfo("FINISH_TIME", taskInfo.getFinishTime());
    task.addOtherInfo("TASK_TYPE", taskInfo.getTaskType());
    task.addOtherInfo("TASK_STATUS", taskInfo.getTaskStatus());
    task.addOtherInfo("ERROR_INFO", taskInfo.getError());

    LOG.info("converted task " + taskInfo.getTaskId() +
        " to a timeline entity");
    return task;
  }

};