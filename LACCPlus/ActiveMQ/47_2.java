//,temp,LoggingBrokerPlugin.java,510,516,temp,LoggingBrokerPlugin.java,255,261
//,3
public class xxx {
    @Override
    public void removeProducer(ConnectionContext context, ProducerInfo info) throws Exception {
        if (isLogAll() || isLogProducerEvents()) {
            LOG.info("Removing Producer: {}", info);
        }
        super.removeProducer(context, info);
    }

};