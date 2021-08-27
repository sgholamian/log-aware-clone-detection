//,temp,AMQ4083Test.java,368,438,temp,AMQ4083Test.java,84,142
//,3
public class xxx {
    @Test
    public void testExpiredMsgsBeforeNonExpired() throws Exception {

        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(connectionUri);
        ActiveMQConnection connection = (ActiveMQConnection) factory.createConnection();
        connection.getPrefetchPolicy().setQueuePrefetch(400);

        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);

        connection.start();

        MessageProducer producer = session.createProducer(queue);
        MessageConsumer consumer = session.createConsumer(queue);

        // send a batch that expires in a short time.
        for (int i = 0; i < 100; i++) {
            producer.send(session.createTextMessage(), DeliveryMode.PERSISTENT, 4, 4000);
        }

        // and send one that doesn't expire to we can ack it.
        producer.send(session.createTextMessage());

        // wait long enough so the first batch times out.
        TimeUnit.SECONDS.sleep(5);

        final QueueViewMBean queueView = getProxyToQueueViewMBean();

        assertEquals(101, queueView.getInFlightCount());

        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                try {
                    message.acknowledge();
                } catch (JMSException e) {
                }
            }
        });

        TimeUnit.SECONDS.sleep(5);

        assertEquals(0, queueView.getInFlightCount());

        for (int i = 0; i < 200; i++) {
            producer.send(session.createTextMessage());
        }

        assertTrue("Inflight count should reach zero, currently: " + queueView.getInFlightCount(), Wait.waitFor(new Wait.Condition() {

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