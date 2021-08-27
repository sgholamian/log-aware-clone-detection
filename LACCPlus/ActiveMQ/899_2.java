//,temp,ListenerTest.java,74,85,temp,PooledSessionExhaustionBlockTimeoutTest.java,113,128
//,3
public class xxx {
    public void sendMessages(ConnectionFactory connectionFactory) throws Exception {
        for (int i = 0; i < NUM_MESSAGES; i++) {
            Connection connection = connectionFactory.createConnection();
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(QUEUE);
            MessageProducer producer = session.createProducer(destination);

            String msgTo = "hello";
            TextMessage message = session.createTextMessage(msgTo);
            producer.send(message);
            connection.close();
            LOG.debug("sent " + i + " messages using " + connectionFactory.getClass());
        }
    }

};