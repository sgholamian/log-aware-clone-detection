//,temp,sample_2959.java,2,16,temp,sample_2965.java,2,19
//,3
public class xxx {
public Void call() {
while (!isShutdown.get() && !Thread.currentThread().isInterrupted()) {
try {
TaskInfo taskInfo = getNextTask();
taskInfo.setInDelayedQueue(false);
processEvictedTask(taskInfo);
} catch (InterruptedException e) {
if (isShutdown.get()) {


log.info("delayedtaskscheduler thread interrupted after shutdown");
}
}
}
}

};