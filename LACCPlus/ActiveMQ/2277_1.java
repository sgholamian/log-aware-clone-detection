//,temp,AMQ4563Test.java,119,148,temp,AMQ4563Test.java,44,72
//,3
public class xxx {
    @Test(timeout = 60000)
    public void testMessagesAreAckedAMQPProducer() throws Exception {
        int messagesSent = 3;
        ActiveMQAdmin.enableJMSFrameTracing();
        assertTrue(brokerService.isPersistent());

        Connection connection = JMSClientContext.INSTANCE.createConnection(amqpURI);
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(name.getMethodName());
        MessageProducer p = session.createProducer(queue);
        TextMessage message = null;
        for (int i=0; i < messagesSent; i++) {
            message = session.createTextMessage();
            String messageText = "Hello " + i + " sent at " + new java.util.Date().toString();
            message.setText(messageText);
            LOG.debug(">>>> Sent [{}]", messageText);
            p.send(message);
        }

        // After the first restart we should get all messages sent above
        restartBroker(connection, session);
        int messagesReceived = readAllMessages(name.getMethodName());
        assertEquals(messagesSent, messagesReceived);

        // This time there should be no messages on this queue
        restartBroker(connection, session);
        QueueViewMBean queueView = getProxyToQueue(name.getMethodName());
        assertEquals(0, queueView.getQueueSize());
    }

};