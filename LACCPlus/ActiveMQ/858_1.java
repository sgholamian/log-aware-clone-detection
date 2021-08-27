//,temp,AMQ6059Test.java,171,179,temp,XACompletionTest.java,1131,1144
//,3
public class xxx {
    private void sendMessage(Destination destination) throws Exception {
        Connection connection = createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageProducer producer = session.createProducer(destination);
        producer.send(destination, session.createTextMessage("DLQ message"), DeliveryMode.PERSISTENT, 4, 1000);
        connection.stop();
        LOG.info("### Send message that will expire.");
    }

};