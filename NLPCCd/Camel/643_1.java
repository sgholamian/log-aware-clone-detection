//,temp,sample_54.java,2,11,temp,sample_6453.java,2,13
//,3
public class xxx {
private void joinThread(Thread thread, long timeoutMsecs) {
try {
thread.join(timeoutMsecs);
} catch (InterruptedException e) {
Thread.currentThread().interrupt();


log.info("interrupted while joining against thread");
}
}

};