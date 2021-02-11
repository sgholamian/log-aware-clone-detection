//,temp,sample_8599.java,2,10,temp,sample_5541.java,2,14
//,3
public class xxx {
private void checkTimeoutOrRetry(MapHost host, IOException ioe) throws IOException {
long currentTime = Time.monotonicNow();
if (retryStartTime == 0) {
retryStartTime = currentTime;
}
if (currentTime - retryStartTime < this.fetchRetryTimeout) {
throw ioe;
} else {


log.info("timeout for copying mapoutput with retry on host after milliseconds");
}
}

};