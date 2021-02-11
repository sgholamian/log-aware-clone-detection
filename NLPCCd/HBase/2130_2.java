//,temp,sample_4923.java,2,8,temp,sample_214.java,2,10
//,3
public class xxx {
private static void sleepBeforeRetry(String msg, int sleepMultiplier, int baseSleepBeforeRetries, int hdfsClientRetriesNumber) throws InterruptedException {
if (sleepMultiplier > hdfsClientRetriesNumber) {
if (LOG.isDebugEnabled()) {


log.info("retries exhausted");
}
}
}

};