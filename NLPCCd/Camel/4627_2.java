//,temp,sample_4502.java,2,14,temp,sample_4501.java,2,13
//,2
public class xxx {
protected void doStop() throws Exception {
super.doStop();
running.set(false);
CountDownLatch consumersShutdownLatch = consumersShutdownLatchRef.get();
if (consumersShutdownLatch != null) {
if (consumersShutdownLatch.await(60, TimeUnit.SECONDS)) {


log.info("timeout waiting on consumer threads to signal completion shutting down");
}
}
}

};