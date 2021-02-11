//,temp,TaskAttemptListenerImpl.java,344,363,temp,TaskAttemptListenerImpl.java,255,269
//,3
public class xxx {
  @Override
  public void preempted(TaskAttemptID taskAttemptID, TaskStatus taskStatus)
          throws IOException, InterruptedException {
    LOG.info("Preempted state update from " + taskAttemptID.toString());
    // An attempt is telling us that it got preempted.
    org.apache.hadoop.mapreduce.v2.api.records.TaskAttemptId attemptID =
        TypeConverter.toYarn(taskAttemptID);

    preemptionPolicy.reportSuccessfulPreemption(attemptID);
    taskHeartbeatHandler.progressing(attemptID);

    context.getEventHandler().handle(
        new TaskAttemptEvent(attemptID,
            TaskAttemptEventType.TA_PREEMPTED));
  }

};