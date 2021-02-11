//,temp,sample_479.java,2,11,temp,sample_313.java,2,11
//,2
public class xxx {
public void scheduleTimeoutTask(long delay) {
if (timeoutTask != null) timeoutTask.cancel();
timeoutTask = new TimeoutTask(this);
timer.schedule(timeoutTask, delay);
if (s_logger.isDebugEnabled()) {


log.info("scheduling timeout at ms");
}
}

};