//,temp,sample_3398.java,2,18,temp,sample_3399.java,2,21
//,3
public class xxx {
public void killTask() {
if (!isCompleted.get()) {
if (!killInvoked.getAndSet(true)) {
synchronized (this) {
TezTaskAttemptID ta = taskSpec.getTaskAttemptID();
LOG.info("Kill task requested for id={}, taskRunnerSetup={}", ta, taskRunner != null);
shouldRunTask = false;
if (taskRunner != null) {
killtimerWatch.start();


log.info("issuing kill to task");
}
}
}
}
}

};