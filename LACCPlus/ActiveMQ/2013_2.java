//,temp,LoggingBrokerPlugin.java,334,341,temp,LoggingBrokerPlugin.java,325,332
//,3
public class xxx {
    @Override
    public org.apache.activemq.broker.region.Destination addDestination(ConnectionContext context,
            ActiveMQDestination destination, boolean create) throws Exception {
        if (isLogAll() || isLogInternalEvents()) {
            LOG.info("Adding destination: {}:{}", destination.getDestinationTypeAsString(), destination.getPhysicalName());
        }
        return super.addDestination(context, destination, create);
    }

};