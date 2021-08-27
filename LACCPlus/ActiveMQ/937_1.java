//,temp,JmsTopicSendReceiveTest.java,37,74,temp,JmsTopicRedeliverTest.java,51,88
//,3
public class xxx {
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        connectionFactory = createConnectionFactory();
        connection = createConnection();
        if (durable) {
            connection.setClientID(getClass().getName());
        }

        LOG.info("Created connection: " + connection);

        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        consumeSession = createConsumerSession();

        LOG.info("Created session: " + session);
        LOG.info("Created consumeSession: " + consumeSession);
        producer = session.createProducer(null);
        producer.setDeliveryMode(deliveryMode);

        LOG.info("Created producer: " + producer + " delivery mode = " + (deliveryMode == DeliveryMode.PERSISTENT ? "PERSISTENT" : "NON_PERSISTENT"));

        if (topic) {
            consumerDestination = session.createTopic(getConsumerSubject());
            producerDestination = session.createTopic(getProducerSubject());
        } else {
            consumerDestination = session.createQueue(getConsumerSubject());
            producerDestination = session.createQueue(getProducerSubject());
        }

        LOG.info("Created  consumer destination: " + consumerDestination + " of type: " + consumerDestination.getClass());
        LOG.info("Created  producer destination: " + producerDestination + " of type: " + producerDestination.getClass());
        consumer = createConsumer();
        consumer.setMessageListener(this);
        startConnection();

        LOG.info("Created connection: " + connection);
    }

};