//,temp,sample_3400.java,2,18,temp,sample_3401.java,2,17
//,3
public class xxx {
public void dummy_method(){
TezTaskAttemptID ta = taskSpec.getTaskAttemptID();
LOG.info("Kill task requested for id={}, taskRunnerSetup={}", ta, taskRunner != null);
shouldRunTask = false;
if (taskRunner != null) {
killtimerWatch.start();
boolean killed = taskRunner.killTask();
if (killed) {
completionListener.fragmentCompleting(getRequestId(), SchedulerFragmentCompletingListener.State.KILLED);
reportTaskKilled();
} else {


log.info("kill request for task did not complete because the task is already complete");
}
}
}

};