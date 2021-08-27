//,temp,NumberOfDestinationsTest.java,48,67,temp,AMQ6293Test.java,134,149
//,3
public class xxx {
    private void sendTestMessages(int numMessages) throws JMSException {
        Session session = connection.createSession(true, Session.SESSION_TRANSACTED);
        MessageProducer producer = session.createProducer(queue);

        final TextMessage textMessage = session.createTextMessage();
        textMessage.setText("Message");
        for (int i = 1; i <= numMessages; i++) {
            producer.send(textMessage);
            if (i % 1000 == 0) {
                LOG.info("Sent {} messages", i);
                session.commit();
            }
        }

        session.close();
    }

};