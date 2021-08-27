//,temp,LoggingBrokerPlugin.java,510,516,temp,LoggingBrokerPlugin.java,255,261
//,3
public class xxx {
    @Override
    public void fastProducer(ConnectionContext context, ProducerInfo producerInfo,ActiveMQDestination destination) {
        if (isLogAll() || isLogProducerEvents() || isLogInternalEvents()) {
            LOG.info("Fast Producer: {}", producerInfo);
        }
        super.fastProducer(context, producerInfo, destination);
    }

};