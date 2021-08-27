//,temp,AMQ4083Test.java,279,366,temp,AMQ4083Test.java,207,277
//,3
public class xxx {
    @Test
    public void testExpiredMsgsInterleavedWithNonExpired() throws Exception {

        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(connectionUri);
        ActiveMQConnection connection = (ActiveMQConnection) factory.createConnection();
        connection.getPrefetchPolicy().setQueuePrefetch(400);

        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);

        connection.start();

        MessageProducer producer = session.createProducer(queue);
        MessageConsumer consumer = session.createConsumer(queue);

        // send a batch that expires in a short time.
        for (int i = 0; i < 200; i++) {

            if ((i % 2) == 0) {
                producer.send(session.createTextMessage(), DeliveryMode.PERSISTENT, 4, 4000);
            } else {
                producer.send(session.createTextMessage());
            }
        }

        // wait long enough so the first batch times out.
        TimeUnit.SECONDS.sleep(5);

        final QueueViewMBean queueView = getProxyToQueueViewMBean();

        assertEquals(200, queueView.getInFlightCount());

        consumer.setMessageListener(new MessageListener() {

            @Override
            public void onMessage(Message message) {
                try {
                    LOG.debug("Acking message: {}", message);
                    message.acknowledge();
                } catch (JMSException e) {
                }
            }
        });

        TimeUnit.SECONDS.sleep(5);

        assertTrue("Inflight count should reach zero, currently: " + queueView.getInFlightCount(), Wait.waitFor(new Wait.Condition() {

            @Override
            public boolean isSatisified() throws Exception {
                return queueView.getInFlightCount() == 0;
            }
        }));

        for (int i = 0; i < 200; i++) {
            producer.send(session.createTextMessage());
        }

        assertTrue("Inflight count should reach zero, currently: " + queueView.getInFlightCount(), Wait.waitFor(new Wait.Condition() {

            @Override
            public boolean isSatisified() throws Exception {
                return queueView.getInFlightCount() == 0;
            }
        }));

        LOG.info("Dequeued Count: {}", queueView.getDequeueCount());
        LOG.info("Dispatch Count: {}", queueView.getDispatchCount());
        LOG.info("Enqueue Count: {}", queueView.getEnqueueCount());
        LOG.info("Expired Count: {}", queueView.getExpiredCount());
        LOG.info("InFlight Count: {}", queueView.getInFlightCount());
    }

};