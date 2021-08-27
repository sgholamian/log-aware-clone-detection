//,temp,PooledConnectionTempQueueTest.java,100,119,temp,PooledConnectionTempQueueTest.java,75,98
//,3
public class xxx {
    private void sendWithReplyToTemp(ConnectionFactory cf, String serviceQueue) throws JMSException,
        InterruptedException {
        Connection con = cf.createConnection();
        con.start();
        Session session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
        TemporaryQueue tempQueue = session.createTemporaryQueue();
        TextMessage msg = session.createTextMessage("Request");
        msg.setJMSReplyTo(tempQueue);
        MessageProducer producer = session.createProducer(session.createQueue(serviceQueue));
        producer.send(msg);

        // This sleep also seems to matter
        Thread.sleep(3000);

        MessageConsumer consumer = session.createConsumer(tempQueue);
        Message replyMsg = consumer.receive();
        LOG.debug("Reply message: {}", replyMsg);

        consumer.close();

        producer.close();
        session.close();
        con.close();
    }

};