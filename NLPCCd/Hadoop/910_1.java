//,temp,sample_3163.java,2,12,temp,sample_5937.java,2,13
//,3
public class xxx {
public void shutdownAndWait() {
shouldRun = false;
slowDiskDetectionDaemon.interrupt();
try {
slowDiskDetectionDaemon.join();
} catch (InterruptedException e) {


log.info("disk outlier detection daemon did not shutdown");
}
}

};