//,temp,sample_3080.java,2,12,temp,sample_5540.java,2,12
//,3
public class xxx {
private void checkTimeoutOrRetry(MapHost host, IOException ioe) throws IOException {
long currentTime = Time.monotonicNow();
if (retryStartTime == 0) {
retryStartTime = currentTime;
}
if (currentTime - retryStartTime < this.fetchRetryTimeout) {


log.info("shuffle output from failed retry it");
}
}

};