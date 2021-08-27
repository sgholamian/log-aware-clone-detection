//,temp,TotalMessageCountTest.java,123,141,temp,AMQ5921Test.java,49,64
//,3
public class xxx {
    private Message receiveMessage() throws JMSException {
        Connection conn = connectionFactory.createConnection();
        Message msg = null;
        try {
            conn.start();
            Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination queue = session.createQueue(TESTQUEUE);
            MessageConsumer consumer = session.createConsumer(queue);
            msg = consumer.receive(TimeUnit.SECONDS.toMillis(10));
            if (msg != null) {
                LOG.info("Message received from " + TESTQUEUE);
            }
            consumer.close();
            session.close();
        } finally {
            conn.close();
        }
        return msg;
    }

};