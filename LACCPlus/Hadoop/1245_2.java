//,temp,TaskAttemptListenerImpl.java,246,257,temp,TaskAttemptListenerImpl.java,216,231
//,3
public class xxx {
  @Override
  public void commitPending(TaskAttemptID taskAttemptID, TaskStatus taskStatsu)
          throws IOException, InterruptedException {
    LOG.info("Commit-pending state update from " + taskAttemptID.toString());
    // An attempt is asking if it can commit its output. This can be decided
    // only by the task which is managing the multiple attempts. So redirect the
    // request there.
    org.apache.hadoop.mapreduce.v2.api.records.TaskAttemptId attemptID =
        TypeConverter.toYarn(taskAttemptID);

    taskHeartbeatHandler.progressing(attemptID);
    //Ignorable TaskStatus? - since a task will send a LastStatusUpdate
    context.getEventHandler().handle(
        new TaskAttemptEvent(attemptID, 
            TaskAttemptEventType.TA_COMMIT_PENDING));
  }

};