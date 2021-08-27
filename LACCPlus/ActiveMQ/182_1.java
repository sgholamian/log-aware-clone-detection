//,temp,JDBCCommitExceptionTest.java,141,153,temp,JMSClientTest.java,608,629
//,3
public class xxx {
    protected void sendMessages(int messagesExpected) throws Exception {
        javax.jms.Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue("TEST");
        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);

        for (int i=0; i<messagesExpected; i++) {
            LOG.debug("Sending message " + (i+1) + " of " + messagesExpected);
            producer.send(session.createTextMessage("test message " + (i+1)));
        }
    }

};