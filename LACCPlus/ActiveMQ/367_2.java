//,temp,AMQ4472Test.java,39,89,temp,AMQ4554Test.java,77,105
//,3
public class xxx {
    public void testMSXProducerTXID() throws Exception {

        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(connectionURI);
        Connection connection = factory.createConnection();
        connection.start();

        Session producerSession = connection.createSession(true, Session.SESSION_TRANSACTED);
        MessageProducer producer = producerSession.createProducer(producerSession.createQueue("myQueue"));
        TextMessage producerMessage = producerSession.createTextMessage("Test Message");
        producer.send(producerMessage);
        producer.close();
        producerSession.commit();
        producerSession.close();

        Session consumerSession = connection.createSession(true, Session.SESSION_TRANSACTED);
        MessageConsumer consumer = consumerSession.createConsumer(consumerSession.createQueue("myQueue"));
        Message consumerMessage = consumer.receive(1000);
        try {
            String txId = consumerMessage.getStringProperty("JMSXProducerTXID");
            assertNotNull(txId);
        } catch(Exception e) {
            LOG.info("Caught Exception that was not expected:", e);
            fail("Should not throw");
        }
        consumer.close();
        consumerSession.commit();
        consumerSession.close();
        connection.close();
    }

};