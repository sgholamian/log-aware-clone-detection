//,temp,TaskAttemptListenerImpl.java,301,317,temp,TaskAttemptListenerImpl.java,284,299
//,3
public class xxx {
  @Override
  public void fsError(TaskAttemptID taskAttemptID, String message)
      throws IOException {
    // This happens only in Child.
    LOG.error("Task: " + taskAttemptID + " - failed due to FSError: "
        + message);
    reportDiagnosticInfo(taskAttemptID, "FSError: " + message);

    org.apache.hadoop.mapreduce.v2.api.records.TaskAttemptId attemptID =
        TypeConverter.toYarn(taskAttemptID);

    // handling checkpoints
    preemptionPolicy.handleFailedContainer(attemptID);

    context.getEventHandler().handle(
        new TaskAttemptFailEvent(attemptID));
  }

};