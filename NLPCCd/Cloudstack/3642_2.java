//,temp,sample_479.java,2,11,temp,sample_313.java,2,11
//,2
public class xxx {
public void scheduleTimeoutTask(long delay) {
if (_timeoutTask != null) _timeoutTask.cancel();
_timeoutTask = new TimeoutTask(this);
_timer.schedule(_timeoutTask, delay);
if (s_logger.isDebugEnabled()) {


log.info("scheduling timeout at ms");
}
}

};