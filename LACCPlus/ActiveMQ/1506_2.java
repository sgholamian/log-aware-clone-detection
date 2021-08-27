//,temp,ConcurrentProducerDurableConsumerTest.java,411,426,temp,AMQ2584ConcurrentDlqTest.java,174,187
//,3
public class xxx {
    private void closeDlqConsumer() throws JMSException, InterruptedException {
        final long limit = System.currentTimeMillis() + 30 * 1000;
        if (dlqConsumerLastReceivedTimeStamp > 0) {
            while (System.currentTimeMillis() < dlqConsumerLastReceivedTimeStamp + 5000
                    && System.currentTimeMillis() < limit) {
                LOG.info("waiting for DLQ do drain, receivedCount: " + dlqReceivedCount);
                TimeUnit.SECONDS.sleep(1);
            }
        }
        if (dlqConnection != null) {
            dlqConnection.close();
            dlqConnection = null;
        }
    }

};