//,temp,sample_2960.java,2,18,temp,sample_2961.java,2,18
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
break;
} else {


log.info("delayedtaskscheduler thread interrupted before being shutdown");
}
}
}
}

};