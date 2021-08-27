//,temp,LoggingBrokerPlugin.java,247,253,temp,LoggingBrokerPlugin.java,182,188
//,3
public class xxx {
    @Override
    public Subscription addConsumer(ConnectionContext context, ConsumerInfo info) throws Exception {
        if (isLogAll() || isLogConsumerEvents()) {
            LOG.info("Adding Consumer: {}", info);
        }
        return super.addConsumer(context, info);
    }

};