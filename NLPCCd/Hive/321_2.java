//,temp,sample_2960.java,2,18,temp,sample_2961.java,2,18
//,3
public class xxx {
public Void call() {
while (!isShutdown.get() && !Thread.currentThread().isInterrupted()) {
try {
NodeInfo nodeInfo = disabledNodesQueue.poll(POLL_TIMEOUT, TimeUnit.MILLISECONDS);
if (nodeInfo != null) {
reenableDisabledNode(nodeInfo);
trySchedulingPendingTasks();
}
} catch (InterruptedException e) {
if (isShutdown.get()) {


log.info("nodeenabler thread interrupted after shutdown");
}
}
}
}

};