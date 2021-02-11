//,temp,TaskAttemptListenerImpl.java,301,317,temp,TaskAttemptListenerImpl.java,284,299
//,3
public class xxx {
  @Override
  public void fatalError(TaskAttemptID taskAttemptID, String msg, boolean fastFail)
      throws IOException {
    // This happens only in Child and in the Task.
    LOG.error("Task: " + taskAttemptID + " - exited : " + msg);
    reportDiagnosticInfo(taskAttemptID, "Error: " + msg);

    org.apache.hadoop.mapreduce.v2.api.records.TaskAttemptId attemptID =
        TypeConverter.toYarn(taskAttemptID);

    // handling checkpoints
    preemptionPolicy.handleFailedContainer(attemptID);

    context.getEventHandler().handle(
        new TaskAttemptFailEvent(attemptID, fastFail));
  }

};