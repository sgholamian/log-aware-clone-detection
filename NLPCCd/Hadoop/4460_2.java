//,temp,sample_3459.java,2,13,temp,sample_8216.java,2,16
//,3
public class xxx {
protected void serviceStop() throws Exception {
if (stopped.getAndSet(true)) {
return;
}
if (eventDispatcherThread != null) {
eventDispatcherThread.interrupt();
try {
eventDispatcherThread.join();
} catch (InterruptedException e) {


log.info("the thread of didn t finish normally");
}
}
}

};