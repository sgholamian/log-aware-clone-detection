//,temp,QueuePurgeTest.java,231,252,temp,QueuePurgeTest.java,191,229
//,3
public class xxx {
    private void testConcurrentPurgeAndSend(boolean prioritizedMessages) throws Exception {
        applyBrokerSpoolingPolicy(false);
        createProducerAndSendMessages(NUM_TO_SEND / 2);
        final QueueViewMBean proxy = getProxyToQueueViewMBean();
        createConsumer();
        final long start = System.currentTimeMillis();
        ExecutorService service = Executors.newFixedThreadPool(1);
        try {
            LOG.info("purging..");
            service.submit(new Runnable() {

                @Override
                public void run() {
                    try {
                        proxy.purge();
                    } catch (Exception e) {
                        fail(e.getMessage());
                    }
                    LOG.info("purge done: " + (System.currentTimeMillis() - start) + "ms");
                }
            });

            //send should get blocked while purge is running
            //which should ensure the metrics are correct
            createProducerAndSendMessages(NUM_TO_SEND / 2);

            Message msg;
            do {
                msg = consumer.receive(1000);
                if (msg != null) {
                    msg.acknowledge();
                }
            } while (msg != null);
            assertEquals("Queue size not valid", 0, proxy.getQueueSize());
            assertEquals("Found messages when browsing", 0, proxy.browseMessages().size());
        } finally {
            service.shutdownNow();
        }
    }

};