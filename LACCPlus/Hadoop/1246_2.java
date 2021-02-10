//,temp,TaskAttemptListenerImpl.java,259,271,temp,TaskAttemptListenerImpl.java,233,244
//,3
public class xxx {
  @Override
  public void done(TaskAttemptID taskAttemptID) throws IOException {
    LOG.info("Done acknowledgement from " + taskAttemptID.toString());

    org.apache.hadoop.mapreduce.v2.api.records.TaskAttemptId attemptID =
        TypeConverter.toYarn(taskAttemptID);

    taskHeartbeatHandler.progressing(attemptID);

    context.getEventHandler().handle(
        new TaskAttemptEvent(attemptID, TaskAttemptEventType.TA_DONE));
  }

};