//,temp,JmsTopicSelectorTest.java,50,80,temp,CompositePublishTest.java,48,96
//,3
public class xxx {
    public void setUp() throws Exception {
        super.setUp();

        connectionFactory = createConnectionFactory();
        connection = createConnection();
        if (durable) {
            connection.setClientID(getClass().getName());
        }

        LOG.info("Created connection: " + connection);

        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        LOG.info("Created session: " + session);

        if (topic) {
            consumerDestination = session.createTopic(getConsumerSubject());
            producerDestination = session.createTopic(getProducerSubject());
        } else {
            consumerDestination = session.createQueue(getConsumerSubject());
            producerDestination = session.createQueue(getProducerSubject());
        }

        LOG.info("Created  consumer destination: " + consumerDestination + " of type: " + consumerDestination.getClass());
        LOG.info("Created  producer destination: " + producerDestination + " of type: " + producerDestination.getClass());
        producer = session.createProducer(producerDestination);
        producer.setDeliveryMode(deliveryMode);

        LOG.info("Created producer: " + producer + " delivery mode = " + (deliveryMode == DeliveryMode.PERSISTENT ? "PERSISTENT" : "NON_PERSISTENT"));
        connection.start();
    }

};