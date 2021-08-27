//,temp,TotalMessageCountTest.java,100,113,temp,PooledSessionExhaustionTest.java,109,124
//,3
public class xxx {
    private void sendMessage() throws JMSException {
        Connection conn = connectionFactory.createConnection();
        try {
            conn.start();
            Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination queue = session.createQueue(TESTQUEUE);
            TextMessage msg = session.createTextMessage("This is a message.");
            MessageProducer producer = session.createProducer(queue);
            producer.send(queue, msg);
            LOG.info("Message sent to " + TESTQUEUE);
        } finally {
            conn.close();
        }
    }

};