//,temp,ReduceMemoryFootprintTest.java,110,140,temp,ReduceMemoryFootprintTest.java,79,108
//,3
public class xxx {
    @Test(timeout = 60000)
    public void testPropertyLostScheduled() throws Exception {

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(connectionURI);
        Connection connection = connectionFactory.createConnection();

        Session session = connection.createSession(true, Session.SESSION_TRANSACTED);
        MessageProducer producer = session.createProducer(new ActiveMQQueue(TEST_QUEUE_NAME));
        connection.start();

        String messageText = createMessageText();

        ActiveMQTextMessage message = new ActiveMQTextMessage();

        // Try with scheduled
        message.setStringProperty(PROP_NAME, PROP_VALUE);
        message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, 1000);

        message.setText(messageText);
        producer.send(message);

        session.commit();

        LOG.info("Attempting to receive scheduled message");
        Message receivedMessage = consumeMessages(connection);

        assertNotNull(receivedMessage);
        assertEquals("property should match", PROP_VALUE, receivedMessage.getStringProperty(PROP_NAME));

        connection.close();
    }

};