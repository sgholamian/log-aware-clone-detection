//,temp,VertxKafkaConsumer.java,57,76,temp,VertxKafkaProducer.java,39,52
//,3
public class xxx {
    @Override
    protected void doStart() throws Exception {
        super.doStart();

        String brokers = getEndpoint().getComponent().getVertxKafkaClientFactory().getBootstrapBrokers(getConfiguration());
        if (brokers != null) {
            LOG.debug("Creating KafkaConsumer connecting to BootstrapBrokers: {}", brokers);
        }

        // create the consumer client
        kafkaConsumer = getEndpoint().getComponent().getVertxKafkaClientFactory()
                .getVertxKafkaConsumer(getEndpoint().getVertx(), getConfiguration().createConsumerConfiguration());

        // create the consumer operation
        final VertxKafkaConsumerOperations consumerOperations
                = new VertxKafkaConsumerOperations(kafkaConsumer, getConfiguration());

        // process our records
        consumerOperations.receiveEvents(this::onEventListener, this::onErrorListener);
    }

};