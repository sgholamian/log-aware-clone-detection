//,temp,FailoverReadInactivityBlockWriteTimeoutClientTest.java,105,120,temp,JMSConcurrentConsumersTest.java,131,146
//,3
public class xxx {
    public void sendMessages(Connection connection, Destination destination, int count, int sleepInterval) throws Exception {
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageProducer producer = session.createProducer(destination);

        for (int i = 0; i < count; i++) {
            TextMessage message = session.createTextMessage();
            message.setText(TEXT_MESSAGE + i);
            LOG.trace("Sending message [" + i + "]");
            producer.send(message);
            if (sleepInterval > 0) {
                Thread.sleep(sleepInterval);
            }
        }

        session.close();
    }

};