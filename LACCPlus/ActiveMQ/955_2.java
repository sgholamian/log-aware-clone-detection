//,temp,JMSClientTest.java,192,233,temp,JMSClientTest.java,163,190
//,3
public class xxx {
    @Test(timeout=30*1000)
    public void testTransactedConsumer() throws Exception {
        ActiveMQAdmin.enableJMSFrameTracing();
        final int msgCount = 1;

        connection = createConnection();
        Session session = connection.createSession(true, Session.SESSION_TRANSACTED);
        Queue queue = session.createQueue(getDestinationName());
        sendMessages(connection, queue, msgCount);

        QueueViewMBean queueView = getProxyToQueue(getDestinationName());
        LOG.info("Queue size after produce is: {}", queueView.getQueueSize());
        assertEquals(msgCount, queueView.getQueueSize());

        MessageConsumer consumer = session.createConsumer(queue);

        Message msg = consumer.receive(TestConfig.TIMEOUT);
        assertNotNull(msg);
        assertTrue(msg instanceof TextMessage);

        LOG.info("Queue size before session commit is: {}", queueView.getQueueSize());
        assertEquals(msgCount, queueView.getQueueSize());

        session.commit();

        LOG.info("Queue size after session commit is: {}", queueView.getQueueSize());
        assertEquals(0, queueView.getQueueSize());
    }

};