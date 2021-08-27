//,temp,TransactedConsumeTest.java,62,76,temp,AmqpAndStompInteropTest.java,114,128
//,3
public class xxx {
    private void sendMessageToQueueUsingAmqp() throws Exception {
        Connection connection = createAmqpConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(getQueueName());
        MessageProducer producer = session.createProducer(queue);

        try {
            TextMessage message = session.createTextMessage("test-message-amqp-source");
            producer.send(message);

            LOG.info("Send AMQP message with Message ID -> {}", message.getJMSMessageID());
        } finally {
            connection.close();
        }
    }

};