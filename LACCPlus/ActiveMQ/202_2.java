//,temp,MessageGroupTest.java,85,107,temp,AmqpAndStompInteropTest.java,165,182
//,3
public class xxx {
    private void readMessageFromQueueUsingStomp() throws Exception {
        Connection connection = createStompConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(getQueueName());
        MessageConsumer consumer = session.createConsumer(queue);

        connection.start();

        Message received = consumer.receive(2000);
        assertNotNull(received);

        LOG.info("Read from STOMP -> message ID = {}", received.getJMSMessageID());

        assertTrue(received instanceof TextMessage);

        TextMessage textMessage = (TextMessage) received;
        assertNotNull(textMessage.getText());
    }

};