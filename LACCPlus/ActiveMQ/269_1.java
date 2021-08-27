//,temp,ConcurrentProducerQueueConsumerTest.java,105,123,temp,ConcurrentProducerDurableConsumerTest.java,116,134
//,3
public class xxx {
            @Override
            public void run() {
                try {
                    MessageConsumer consumer = null;
                    for (int i = 0; i < consumersToActivate; i++) {
                        LOG.info("Waiting for add signal from producer...");
                        synchronized (addConsumerSignal) {
                            addConsumerSignal.wait(30 * 60 * 1000);
                        }
                        TimedMessageListener listener = new TimedMessageListener();
                        consumer = createConsumer(factory.createConnection(), destination);
                        LOG.info("Created consumer " + consumer);
                        consumer.setMessageListener(listener);
                        consumers.put(consumer, listener);
                    }
                } catch (Exception e) {
                    LOG.error("failed to start consumer", e);
                }
            }

};