//,temp,AMQ4485Test.java,165,176,temp,AMQ5822Test.java,59,82
//,3
public class xxx {
    @Test
    public void testReadCounter() throws Exception {
        LOG.info("Connecting to: {}", connectionUri);

        byte[] payload = new byte[50 * 1024 * 1024];

        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(connectionUri + "?useInactivityMonitor=false");
        final ActiveMQConnection connection = (ActiveMQConnection) factory.createConnection();
        connection.start();

        LOG.info("Connected to: {}", connection.getTransport());

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("test");
        MessageProducer producer = session.createProducer(queue);
        BytesMessage message = session.createBytesMessage();

        message.writeBytes(payload);

        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        producer.send(message);

        connection.close();
    }

};