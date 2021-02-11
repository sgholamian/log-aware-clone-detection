//,temp,sample_8938.java,2,15,temp,sample_8937.java,2,14
//,3
public class xxx {
public void wakeupExisting() throws InterruptedException {
final ConstantTimeBackoff backoff = new ConstantTimeBackoff();
backoff.setTimeToWait(10);
Thread thread = new Thread(new Runnable() {
public void run() {
backoff.waitBeforeRetry();
}
});
thread.start();


log.info("thread started");
}

};