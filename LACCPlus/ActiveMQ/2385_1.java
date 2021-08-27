//,temp,ExpiredMessagesWithNoConsumerTest.java,198,255,temp,ExpiredMessagesWithNoConsumerTest.java,117,190
//,3
public class xxx {
    public void testExpiredMessagesWithNoConsumer() throws Exception {

        createBrokerWithMemoryLimit();

        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(connectionUri);
        connection = factory.createConnection();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        producer = session.createProducer(destination);
        producer.setTimeToLive(1000);
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

        final DestinationViewMBean view = createView(destination);
        Wait.waitFor(new Wait.Condition() {
            @Override
            public boolean isSatisified() throws Exception {
                LOG.info("enqueue=" + view.getEnqueueCount() + ", dequeue=" + view.getDequeueCount()
                        + ", inflight=" + view.getInFlightCount() + ", expired= " + view.getExpiredCount()
                        + ", size= " + view.getQueueSize());
                return sendCount == view.getExpiredCount();
            }
        }, Wait.MAX_WAIT_MILLIS * 10);
        LOG.info("enqueue=" + view.getEnqueueCount() + ", dequeue=" + view.getDequeueCount()
                + ", inflight=" + view.getInFlightCount() + ", expired= " + view.getExpiredCount()
                + ", size= " + view.getQueueSize());

        assertEquals("Not all sent messages have expired", sendCount, view.getExpiredCount());
        assertEquals("memory usage doesn't go to duck egg", 0, view.getMemoryPercentUsage());
    }

};