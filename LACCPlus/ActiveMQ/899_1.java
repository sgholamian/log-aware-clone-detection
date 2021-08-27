//,temp,ListenerTest.java,74,85,temp,PooledSessionExhaustionBlockTimeoutTest.java,113,128
//,3
public class xxx {
    public void sendMessages(String destName, int msgNum) throws Exception {
        ConnectionFactory factory = new org.apache.activemq.ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection conn = factory.createConnection();
        Session sess = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination dest = sess.createQueue(destName);
        MessageProducer producer = sess.createProducer(dest);
        for (int i = 0; i < msgNum; i++) {
            String messageText = i +" test";
            LOG.info("sending message '" + messageText + "'");
            producer.send(sess.createTextMessage(messageText));
        }
    }

};