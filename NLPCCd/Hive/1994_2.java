//,temp,sample_2194.java,2,9,temp,sample_2202.java,2,9
//,2
public class xxx {
public void unregisterRunningTaskAttempt(final TezTaskAttemptID taskAttemptId, TaskAttemptEndReason endReason, String diagnostics) {
super.unregisterRunningTaskAttempt(taskAttemptId, endReason, diagnostics);
if (endReason == TaskAttemptEndReason.INTERNAL_PREEMPTION) {


log.info("processing taskend for task caused by internal preemption");
}
}

};