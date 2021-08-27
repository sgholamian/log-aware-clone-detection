//,temp,sample_54.java,2,11,temp,sample_6453.java,2,13
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