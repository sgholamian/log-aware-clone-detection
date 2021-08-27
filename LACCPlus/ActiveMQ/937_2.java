//,temp,JmsTopicSendReceiveTest.java,37,74,temp,JmsTopicRedeliverTest.java,51,88
//,3
public class xxx {
    protected void setUp() throws Exception {
        super.setUp();

        connectionFactory = createConnectionFactory();
        connection = createConnection();
        initRedeliveryDelay = ((ActiveMQConnection)connection).getRedeliveryPolicy().getInitialRedeliveryDelay();

        if (durable) {
            connection.setClientID(getClass().getName());
        }

        LOG.info("Created connection: " + connection);

        session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        consumeSession = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);

        LOG.info("Created session: " + session);
        LOG.info("Created consumeSession: " + consumeSession);
        producer = session.createProducer(null);
        // producer.setDeliveryMode(deliveryMode);

        LOG.info("Created producer: " + producer);

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
        connection.start();

        LOG.info("Created connection: " + connection);
    }

};