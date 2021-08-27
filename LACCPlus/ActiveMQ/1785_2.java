//,temp,JMSClientTransactionTest.java,207,292,temp,JMSClientTransactionTest.java,160,205
//,3
public class xxx {
    @Test(timeout = 60000)
    public void testQueueTXRollbackAndCommit() throws Exception {
        final int MSG_COUNT = 3;

        connection = createConnection();
        connection.start();

        Session session = connection.createSession(true, Session.SESSION_TRANSACTED);
        Queue destination = session.createQueue(getDestinationName());

        MessageProducer producer = session.createProducer(destination);
        MessageConsumer consumer = session.createConsumer(destination);

        for (int i = 1; i <= MSG_COUNT; i++) {
            LOG.info("Sending message: {} to rollback", i);
            TextMessage message = session.createTextMessage("Rolled back Message: " + i);
            message.setIntProperty("MessageSequence", i);
            producer.send(message);
        }

        session.rollback();

        assertEquals(0, getProxyToQueue(getDestinationName()).getQueueSize());

        for (int i = 1; i <= MSG_COUNT; i++) {
            LOG.info("Sending message: {} to commit", i);
            TextMessage message = session.createTextMessage("Commit Message: " + i);
            message.setIntProperty("MessageSequence", i);
            producer.send(message);
        }

        session.commit();

        assertEquals(MSG_COUNT, getProxyToQueue(getDestinationName()).getQueueSize());
        SubscriptionViewMBean subscription = getProxyToQueueSubscriber(getDestinationName());
        assertNotNull(subscription);

        for (int i = 1; i <= MSG_COUNT; i++) {
            LOG.info("Trying to receive message: {}", i);
            TextMessage message = (TextMessage) consumer.receive(1000);
            assertNotNull("Message " + i + " should be available", message);
            assertEquals("Should get message: " + i, i, message.getIntProperty("MessageSequence"));
        }

        session.commit();
    }

};