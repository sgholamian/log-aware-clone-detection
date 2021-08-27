//,temp,LoggingBrokerPlugin.java,247,253,temp,LoggingBrokerPlugin.java,182,188
//,3
public class xxx {
    @Override
    public void removeConsumer(ConnectionContext context, ConsumerInfo info) throws Exception {
        if (isLogAll() || isLogConsumerEvents()) {
            LOG.info("Removing Consumer: {}", info);
        }
        super.removeConsumer(context, info);
    }

};