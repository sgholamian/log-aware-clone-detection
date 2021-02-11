//,temp,TaskAttemptListenerImpl.java,259,271,temp,TaskAttemptListenerImpl.java,246,257
//,2
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