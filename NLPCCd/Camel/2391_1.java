//,temp,sample_69.java,2,14,temp,sample_6475.java,2,14
//,2
public class xxx {
private void waitFor(long millis) {
Object lock = new Object();
synchronized (lock) {
try {
lock.wait(millis);
} catch (InterruptedException e) {
Thread.currentThread().interrupt();


log.info("spuriously interrupted while waiting for ms");
}
}
}

};