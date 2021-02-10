//,temp,TaskAttemptListenerImpl.java,306,325,temp,TaskAttemptListenerImpl.java,233,244
//,3
public class xxx {
  @Override
  public void reportDiagnosticInfo(TaskAttemptID taskAttemptID, String diagnosticInfo)
 throws IOException {
    diagnosticInfo = StringInterner.weakIntern(diagnosticInfo);
    LOG.info("Diagnostics report from " + taskAttemptID.toString() + ": "
        + diagnosticInfo);

    org.apache.hadoop.mapreduce.v2.api.records.TaskAttemptId attemptID =
      TypeConverter.toYarn(taskAttemptID);
    taskHeartbeatHandler.progressing(attemptID);

    // This is mainly used for cases where we want to propagate exception traces
    // of tasks that fail.

    // This call exists as a hadoop mapreduce legacy wherein all changes in
    // counters/progress/phase/output-size are reported through statusUpdate()
    // call but not diagnosticInformation.
    context.getEventHandler().handle(
        new TaskAttemptDiagnosticsUpdateEvent(attemptID, diagnosticInfo));
  }

};