//,temp,sample_4991.java,2,16,temp,sample_4993.java,2,16
//,3
public class xxx {
public void dummy_method(){
task.setCreatedTime(taskInfo.getStartTime());
task.addInfo("START_TIME", taskInfo.getStartTime());
task.addInfo("FINISH_TIME", taskInfo.getFinishTime());
task.addInfo("TASK_TYPE", taskInfo.getTaskType());
task.addInfo("TASK_STATUS", taskInfo.getTaskStatus());
task.addInfo("ERROR_INFO", taskInfo.getError());
Counters counters = taskInfo.getCounters();
if (counters != null) {
addMetrics(task, counters);
}


log.info("converted task to a timeline entity");
}

};