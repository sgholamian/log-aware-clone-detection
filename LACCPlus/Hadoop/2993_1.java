//,temp,TimelineEntityConverterV1.java,93,107,temp,TimelineEntityConverterV2.java,125,139
//,3
public class xxx {
  private Set<TimelineEntity>
      createTaskAndTaskAttemptEntities(JobInfo jobInfo) {
    Set<TimelineEntity> entities = new HashSet<>();
    Map<TaskID, TaskInfo> taskInfoMap = jobInfo.getAllTasks();
    LOG.info("job " + jobInfo.getJobId()+ " has " + taskInfoMap.size() +
        " tasks");
    for (TaskInfo taskInfo: taskInfoMap.values()) {
      TimelineEntity task = createTaskEntity(taskInfo);
      entities.add(task);
      // add the task attempts from this task
      Set<TimelineEntity> taskAttempts = createTaskAttemptEntities(taskInfo);
      entities.addAll(taskAttempts);
    }
    return entities;
  }

};