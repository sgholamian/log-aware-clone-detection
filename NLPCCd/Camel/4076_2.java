//,temp,sample_5788.java,2,19,temp,sample_5787.java,2,15
//,3
public class xxx {
protected void doRun() {
BlockingQueue<Exchange> queue = endpoint.getQueue();
while (queue != null && isRunAllowed()) {
if (getEndpoint().getCamelContext().getStatus().isStarting()) {
try {
Thread.sleep(Math.min(pollTimeout, 1000));
} catch (InterruptedException e) {


log.info("sleep interrupted are we stopping");
}
}
}
}

};