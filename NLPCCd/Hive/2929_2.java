//,temp,sample_3400.java,2,18,temp,sample_3401.java,2,17
//,3
public class xxx {
public void dummy_method(){
shouldRunTask = false;
if (taskRunner != null) {
killtimerWatch.start();
boolean killed = taskRunner.killTask();
if (killed) {
completionListener.fragmentCompleting(getRequestId(), SchedulerFragmentCompletingListener.State.KILLED);
reportTaskKilled();
} else {
}
} else {


log.info("reporting taskkilled for non started fragment");
}
}

};