//,temp,JDBCCommitExceptionTest.java,141,153,temp,JMSClientTest.java,608,629
//,3
public class xxx {
    @Test(timeout=30000)
    public void testBrokerRestartWontHangConnectionClose() throws Exception {
        connection = createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(getDestinationName());
        connection.start();

        MessageProducer producer = session.createProducer(queue);
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);

        Message m = session.createTextMessage("Sample text");
        producer.send(m);

        restartBroker();

        try {
            connection.close();
        } catch (Exception ex) {
            LOG.error("Should not thrown on disconnected connection close(): {}", ex);
            fail("Should not have thrown an exception.");
        }
    }

};