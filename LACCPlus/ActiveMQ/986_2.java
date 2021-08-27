//,temp,JmsTopicSelectorTest.java,50,80,temp,CompositePublishTest.java,48,96
//,3
public class xxx {
    @SuppressWarnings("unchecked")
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        connectionFactory = createConnectionFactory();

        sendConnection = createConnection();
        sendConnection.start();

        receiveConnection = createConnection();
        receiveConnection.start();

        LOG.info("Created sendConnection: " + sendConnection);
        LOG.info("Created receiveConnection: " + receiveConnection);

        session = sendConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        receiveSession = receiveConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        LOG.info("Created sendSession: " + session);
        LOG.info("Created receiveSession: " + receiveSession);

        producer = session.createProducer(null);

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

        Destination[] destinations = getDestinations();
        consumers = new MessageConsumer[destinations.length];
        messageLists = new List[destinations.length];
        for (int i = 0; i < destinations.length; i++) {
            Destination dest = destinations[i];
            messageLists[i] = createConcurrentList();
            consumers[i] = receiveSession.createConsumer(dest);
            consumers[i].setMessageListener(createMessageListener(i, messageLists[i]));
        }

        LOG.info("Started connections");
    }

};