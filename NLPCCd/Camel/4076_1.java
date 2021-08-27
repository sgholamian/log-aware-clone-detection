//,temp,sample_5788.java,2,19,temp,sample_5787.java,2,15
//,3
public class xxx {
public void dummy_method(){
while (queue != null && isRunAllowed()) {
if (getEndpoint().getCamelContext().getStatus().isStarting()) {
try {
Thread.sleep(Math.min(pollTimeout, 1000));
} catch (InterruptedException e) {
}
continue;
}
if (isSuspending() || isSuspended() || isStarting()) {
if (shutdownPending && queue.isEmpty()) {


log.info("consumer is suspended and shutdown is pending so this consumer thread is breaking out because the task queue is empty");
}
}
}
}

};