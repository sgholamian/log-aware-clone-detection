//,temp,AMQ6059Test.java,171,179,temp,XACompletionTest.java,1131,1144
//,3
public class xxx {
    protected void sendMessagesWithTo(ConnectionFactory factory, int messagesExpected, Destination destination) throws Exception {
        javax.jms.Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);

        for (int i = 0; i < messagesExpected; i++) {
            LOG.debug("Sending message " + (i + 1) + " of " + messagesExpected);
            producer.send(session.createTextMessage("test message " + (i + 1)));
        }
        connection.close();
    }

};