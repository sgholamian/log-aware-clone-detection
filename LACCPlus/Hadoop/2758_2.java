//,temp,TaskAttemptListenerImpl.java,344,363,temp,TaskAttemptListenerImpl.java,271,282
//,3
public class xxx {
  @Override
  public void done(TaskAttemptID taskAttemptID) throws IOException {
    LOG.info("Done acknowledgment from " + taskAttemptID.toString());

    org.apache.hadoop.mapreduce.v2.api.records.TaskAttemptId attemptID =
        TypeConverter.toYarn(taskAttemptID);

    taskHeartbeatHandler.progressing(attemptID);

    context.getEventHandler().handle(
        new TaskAttemptEvent(attemptID, TaskAttemptEventType.TA_DONE));
  }

};