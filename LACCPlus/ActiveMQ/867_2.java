//,temp,MemMessageIdList.java,121,145,temp,MessageIdList.java,141,165
//,3
public class xxx {
    public void waitForMessagesToArrive(int messageCount, long maximumDuration) {
        LOG.info("Waiting for " + messageCount + " message(s) to arrive");

        long start = System.currentTimeMillis();

        for (int i = 0; i < messageCount; i++) {
            try {
                if (hasReceivedMessages(messageCount)) {
                    break;
                }
                long duration = System.currentTimeMillis() - start;
                if (duration >= maximumDuration) {
                    break;
                }
                synchronized (semaphore) {
                    semaphore.wait(maximumDuration - duration);
                }
            } catch (InterruptedException e) {
                LOG.info("Caught: " + e);
            }
        }
        long end = System.currentTimeMillis() - start;

        LOG.info("End of wait for " + end + " millis and received: " + getMessageCount() + " messages");
    }

};