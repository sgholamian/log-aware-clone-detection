//,temp,GenericFilePollingConsumer.java,226,232,temp,DelayProcessorSupport.java,252,258
//,2
public class xxx {
    private void sleep(long delay) throws InterruptedException {
        if (delay <= 0) {
            return;
        }
        LOG.trace("Sleeping for: {} millis", delay);
        Thread.sleep(delay);
    }

};