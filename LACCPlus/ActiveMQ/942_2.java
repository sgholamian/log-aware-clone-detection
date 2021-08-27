//,temp,AMQ4563Test.java,97,117,temp,AMQ4563Test.java,74,95
//,3
public class xxx {
    @Test(timeout = 60000)
    public void testSelectingOnAMQPMessageID() throws Exception {
        ActiveMQAdmin.enableJMSFrameTracing();
        assertTrue(brokerService.isPersistent());

        Connection connection = JMSClientContext.INSTANCE.createConnection(amqpURI);
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(name.getMethodName());
        MessageProducer p = session.createProducer(queue);
        TextMessage message = session.createTextMessage();
        String messageText = "Hello sent at " + new java.util.Date().toString();
        message.setText(messageText);
        p.send(message);

        // Restart broker.
        restartBroker(connection, session);
        String selector = "JMSMessageID = '" + message.getJMSMessageID() + "'";
        LOG.info("Using selector: {}", selector);
        int messagesReceived = readAllMessages(name.getMethodName(), selector);
        assertEquals(1, messagesReceived);
    }

};