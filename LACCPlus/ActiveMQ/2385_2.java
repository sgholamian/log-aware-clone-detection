//,temp,ExpiredMessagesWithNoConsumerTest.java,198,255,temp,ExpiredMessagesWithNoConsumerTest.java,117,190
//,3
public class xxx {
    public void testExpiredNonPersistentMessagesWithNoConsumer() throws Exception {

        createBrokerWithMemoryLimit(2000);

        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(connectionUri);
        connection = factory.createConnection();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        producer = session.createProducer(destination);
        producer.setTimeToLive(1000);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        connection.start();
        final long sendCount = 2000;

        final Thread producingThread = new Thread("Producing Thread") {
            @Override
            public void run() {
                try {
                    int i = 0;
                    long tStamp = System.currentTimeMillis();
                    while (i++ < sendCount) {
                        producer.send(session.createTextMessage("test"));
                        if (i%100 == 0) {
                            LOG.info("sent: " + i + " @ " + ((System.currentTimeMillis() - tStamp) / 100)  + "m/ms");
                            tStamp = System.currentTimeMillis() ;
                        }

                        if (135 == i) {
                            // allow pending messages to expire, before usage limit kicks in  to flush them
                            TimeUnit.SECONDS.sleep(5);
                        }
                    }
                } catch (Throwable ex) {
                    ex.printStackTrace();
                }
            }
        };

        producingThread.start();

        assertTrue("producer failed to complete within allocated time", Wait.waitFor(new Wait.Condition() {
            @Override
            public boolean isSatisified() throws Exception {
                producingThread.join(TimeUnit.SECONDS.toMillis(3000));
                return !producingThread.isAlive();
            }
        }));

        TimeUnit.SECONDS.sleep(5);

        final DestinationViewMBean view = createView(destination);
        Wait.waitFor(new Wait.Condition() {
            @Override
            public boolean isSatisified() throws Exception {
                try {
                LOG.info("enqueue=" + view.getEnqueueCount() + ", dequeue=" + view.getDequeueCount()
                        + ", inflight=" + view.getInFlightCount() + ", expired= " + view.getExpiredCount()
                        + ", size= " + view.getQueueSize());
                return view.getDequeueCount() != 0
                        && view.getDequeueCount() == view.getExpiredCount()
                        && view.getDequeueCount() == view.getEnqueueCount()
                        && view.getQueueSize() == 0;
                } catch (Exception ignored) {
                    LOG.info(ignored.toString());
                }
                return false;
            }
        }, Wait.MAX_WAIT_MILLIS * 10);
        LOG.info("enqueue=" + view.getEnqueueCount() + ", dequeue=" + view.getDequeueCount()
                + ", inflight=" + view.getInFlightCount() + ", expired= " + view.getExpiredCount()
                + ", size= " + view.getQueueSize());

        assertEquals("memory usage doesn't go to duck egg", 0, view.getMemoryPercentUsage());
        assertEquals("0 queue", 0, view.getQueueSize());
    }

};