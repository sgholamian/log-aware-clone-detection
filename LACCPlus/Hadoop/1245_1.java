//,temp,TaskAttemptListenerImpl.java,246,257,temp,TaskAttemptListenerImpl.java,216,231
//,3
public class xxx {
  @Override
  public void fatalError(TaskAttemptID taskAttemptID, String msg)
      throws IOException {
    // This happens only in Child and in the Task.
    LOG.fatal("Task: " + taskAttemptID + " - exited : " + msg);
    reportDiagnosticInfo(taskAttemptID, "Error: " + msg);

    org.apache.hadoop.mapreduce.v2.api.records.TaskAttemptId attemptID =
        TypeConverter.toYarn(taskAttemptID);
    context.getEventHandler().handle(
        new TaskAttemptEvent(attemptID, TaskAttemptEventType.TA_FAILMSG));
  }

};