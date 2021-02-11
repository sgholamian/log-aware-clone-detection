//,temp,sample_4991.java,2,16,temp,sample_4993.java,2,16
//,3
public class xxx {
public void dummy_method(){
taskAttempt.addInfo("SHUFFLE_FINISH_TIME", taskAttemptInfo.getShuffleFinishTime());
taskAttempt.addInfo("SORT_FINISH_TIME", taskAttemptInfo.getSortFinishTime());
taskAttempt.addInfo("TASK_STATUS", taskAttemptInfo.getTaskStatus());
taskAttempt.addInfo("STATE", taskAttemptInfo.getState());
taskAttempt.addInfo("ERROR", taskAttemptInfo.getError());
taskAttempt.addInfo("CONTAINER_ID", taskAttemptInfo.getContainerId().toString());
Counters counters = taskAttemptInfo.getCounters();
if (counters != null) {
addMetrics(taskAttempt, counters);
}


log.info("converted task attempt to a timeline entity");
}

};