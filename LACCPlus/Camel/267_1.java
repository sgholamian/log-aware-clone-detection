//,temp,FailoverConsumerStrategy.java,47,64,temp,KeySharedConsumerStrategy.java,47,64
//,2
public class xxx {
    private Collection<Consumer<byte[]>> createMultipleConsumers(final PulsarEndpoint pulsarEndpoint) {
        final Collection<Consumer<byte[]>> consumers = new LinkedList<>();
        final PulsarConfiguration configuration = pulsarEndpoint.getPulsarConfiguration();

        for (int i = 0; i < configuration.getNumberOfConsumers(); i++) {
            final String consumerName = configuration.getConsumerNamePrefix() + i;
            try {
                ConsumerBuilder<byte[]> builder
                        = CommonCreationStrategyImpl.create(consumerName, pulsarEndpoint, pulsarConsumer);

                consumers.add(builder.subscriptionType(SubscriptionType.Failover).subscribe());
            } catch (PulsarClientException exception) {
                LOGGER.error("A PulsarClientException occurred when creating Consumer {}, {}", consumerName,
                        exception.getMessage(), exception);
            }
        }
        return consumers;
    }

};