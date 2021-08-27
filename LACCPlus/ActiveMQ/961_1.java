//,temp,AmqpScheduledMessageTest.java,266,288,temp,XACompletionTest.java,565,585
//,3
public class xxx {
    public void readMessages(String destinationName, int count, boolean topic, long timeout) throws Exception {
        Connection connection = createJMSConnection();
        connection.start();

        try {
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = null;
            if (topic) {
                destination = session.createTopic(destinationName);
            } else {
                destination = session.createQueue(destinationName);
            }

            MessageConsumer consumer = session.createConsumer(destination);
            for (int i = 1; i <= count; i++) {
                Message received = consumer.receive(timeout);
                assertNotNull(received);
                LOG.info("Read next message: {}", received.getJMSMessageID());
            }
        } finally {
            connection.close();
        }
    }

};