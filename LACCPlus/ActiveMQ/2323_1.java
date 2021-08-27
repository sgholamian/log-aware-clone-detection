//,temp,QueuePurgeTest.java,231,252,temp,QueuePurgeTest.java,191,229
//,3
public class xxx {
    private void testPurgeLargeQueueWithConsumer(boolean prioritizedMessages) throws Exception {
        applyBrokerSpoolingPolicy(prioritizedMessages);
        createProducerAndSendMessages(NUM_TO_SEND);
        QueueViewMBean proxy = getProxyToQueueViewMBean();
        createConsumer();
        long start = System.currentTimeMillis();
        LOG.info("purging..");
        proxy.purge();
        LOG.info("purge done: " + (System.currentTimeMillis() - start) + "ms");
        assertEquals("Queue size is not zero, it's " + proxy.getQueueSize(), 0,
                proxy.getQueueSize());
        assertEquals("usage goes to duck", 0, proxy.getMemoryPercentUsage());
        Message msg;
        do {
            msg = consumer.receive(1000);
            if (msg != null) {
                msg.acknowledge();
            }
        } while (msg != null);
        assertEquals("Queue size not valid", 0, proxy.getQueueSize());
        assertEquals("Found messages when browsing", 0, proxy.browseMessages().size());
    }

};