//,temp,sample_4503.java,2,15,temp,sample_4500.java,2,11
//,3
public class xxx {
protected void doStop() throws Exception {
super.doStop();
running.set(false);
CountDownLatch consumersShutdownLatch = consumersShutdownLatchRef.get();
if (consumersShutdownLatch != null) {


log.info("stop signalled waiting on consumers to shut down");
}
}

};