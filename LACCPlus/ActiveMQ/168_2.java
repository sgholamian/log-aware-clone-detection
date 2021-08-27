//,temp,SlowConsumerTest.java,69,83,temp,AmqpAndStompInteropTest.java,130,144
//,3
public class xxx {
    private void sendMessageToQueueUsingStomp() throws Exception {
        Connection connection = createStompConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(getQueueName());
        MessageProducer producer = session.createProducer(queue);

        try {
            TextMessage message = session.createTextMessage("test-message-stomp-source");
            producer.send(message);

            LOG.info("Send STOMP message with Message ID -> {}", message.getJMSMessageID());
        } finally {
            connection.close();
        }
    }

};