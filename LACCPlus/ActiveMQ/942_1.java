//,temp,AMQ4563Test.java,97,117,temp,AMQ4563Test.java,74,95
//,3
public class xxx {
    @Test(timeout = 60000)
    public void testSelectingOnActiveMQMessageID() throws Exception {
        ActiveMQAdmin.enableJMSFrameTracing();
        assertTrue(brokerService.isPersistent());

        Connection connection = createAMQConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue(name.getMethodName());
        MessageProducer p = session.createProducer(destination);
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