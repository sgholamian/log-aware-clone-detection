//,temp,sample_4503.java,2,15,temp,sample_4500.java,2,11
//,3
public class xxx {
protected void doStop() throws Exception {
super.doStop();
running.set(false);
CountDownLatch consumersShutdownLatch = consumersShutdownLatchRef.get();
if (consumersShutdownLatch != null) {
if (consumersShutdownLatch.await(60, TimeUnit.SECONDS)) {
} else {
}
} else {


log.info("stop signalled while there are no consumers yet so no need to wait for consumers");
}
}

};