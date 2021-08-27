//,temp,JMSClientTest.java,480,500,temp,RetroactiveConsumerWithMessageQueryTest.java,47,69
//,3
public class xxx {
    @Test(timeout=30000)
    public void testConsumerCreateThrowsWhenBrokerStops() throws Exception {
        connection = createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(getDestinationName());
        connection.start();

        MessageProducer producer = session.createProducer(queue);
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);

        Message m = session.createTextMessage("Sample text");
        producer.send(m);

        stopBroker();
        try {
            session.createConsumer(queue);
            fail("Should have thrown an IllegalStateException");
        } catch (Exception ex) {
            LOG.info("Caught exception on consumer create: {}", ex);
        }
    }

};