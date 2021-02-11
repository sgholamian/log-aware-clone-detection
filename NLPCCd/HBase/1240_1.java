//,temp,sample_215.java,2,13,temp,sample_367.java,2,8
//,3
public class xxx {
private static void sleepBeforeRetry(String msg, int sleepMultiplier, int baseSleepBeforeRetries, int hdfsClientRetriesNumber) throws InterruptedException {
if (sleepMultiplier > hdfsClientRetriesNumber) {
if (LOG.isDebugEnabled()) {
}
return;
}
if (LOG.isDebugEnabled()) {


log.info("sleeping times");
}
}

};