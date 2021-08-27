//,temp,VertxKafkaConsumer.java,57,76,temp,VertxKafkaProducer.java,39,52
//,3
public class xxx {
    @Override
    protected void doStart() {
        String brokers = getEndpoint().getComponent().getVertxKafkaClientFactory().getBootstrapBrokers(getConfiguration());
        if (brokers != null) {
            LOG.debug("Creating KafkaConsumer connecting to BootstrapBrokers: {}", brokers);
        }

        // create kafka client
        kafkaProducer = getEndpoint().getComponent().getVertxKafkaClientFactory()
                .getVertxKafkaProducer(getEndpoint().getVertx(), getConfiguration().createProducerConfiguration());

        // create our operations
        producerOperations = new VertxKafkaProducerOperations(kafkaProducer, getConfiguration());
    }

};