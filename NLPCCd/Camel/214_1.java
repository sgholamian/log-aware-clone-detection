//,temp,sample_55.java,2,13,temp,sample_6452.java,2,11
//,3
public class xxx {
private void joinThread(Thread thread, long timeoutMsecs) {
try {
thread.join(timeoutMsecs);
} catch (InterruptedException e) {
Thread.currentThread().interrupt();
}
if (thread.isAlive()) {


log.info("thread was interrupted but is still alive after waiting at least msecs");
}
}

};