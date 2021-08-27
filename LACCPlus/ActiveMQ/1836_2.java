//,temp,ConcurrentProducerQueueConsumerTest.java,172,207,temp,ConcurrentProducerDurableConsumerTest.java,166,202
//,3
public class xxx {
    public void x_testSendWithInactiveAndActiveConsumers() throws Exception {
        Destination destination = createDestination();
        ConnectionFactory factory = createConnectionFactory();
        startInactiveConsumers(factory, destination);

        Connection connection = factory.createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);

        final int toSend = 100;
        final int numIterations = 5;

        double[] noConsumerStats = produceMessages(destination, toSend, numIterations, session, producer, null);

        startConsumers(factory, destination);
        LOG.info("Activated consumer");

        double[] withConsumerStats = produceMessages(destination, toSend, numIterations, session, producer, null);

        LOG.info("With consumer: " + withConsumerStats[1] + " , with noConsumer: " + noConsumerStats[1] + ", multiplier: "
            + (withConsumerStats[1] / noConsumerStats[1]));
        final int reasonableMultiplier = 15; // not so reasonable but improving
        assertTrue("max X times as slow with consumer: " + withConsumerStats[1] + ", with no Consumer: " + noConsumerStats[1] + ", multiplier: "
            + (withConsumerStats[1] / noConsumerStats[1]), withConsumerStats[1] < noConsumerStats[1] * reasonableMultiplier);

        final int toReceive = toSend * numIterations * consumerCount * 2;
        Wait.waitFor(new Wait.Condition() {
            @Override
            public boolean isSatisified() throws Exception {
                LOG.info("count: " + allMessagesList.getMessageCount());
                return toReceive == allMessagesList.getMessageCount();
            }
        }, 60 * 1000);

        assertEquals("got all messages", toReceive, allMessagesList.getMessageCount());
    }

};