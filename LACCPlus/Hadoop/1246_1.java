//,temp,TaskAttemptListenerImpl.java,259,271,temp,TaskAttemptListenerImpl.java,233,244
//,3
public class xxx {
  @Override
  public void fsError(TaskAttemptID taskAttemptID, String message)
      throws IOException {
    // This happens only in Child.
    LOG.fatal("Task: " + taskAttemptID + " - failed due to FSError: "
        + message);
    reportDiagnosticInfo(taskAttemptID, "FSError: " + message);

    org.apache.hadoop.mapreduce.v2.api.records.TaskAttemptId attemptID =
        TypeConverter.toYarn(taskAttemptID);
    context.getEventHandler().handle(
        new TaskAttemptEvent(attemptID, TaskAttemptEventType.TA_FAILMSG));
  }

};