//,temp,LoggingBrokerPlugin.java,334,341,temp,LoggingBrokerPlugin.java,325,332
//,3
public class xxx {
    @Override
    public void removeDestination(ConnectionContext context, ActiveMQDestination destination, long timeout)
            throws Exception {
        if (isLogAll() || isLogInternalEvents()) {
            LOG.info("Removing destination: {}:{}", destination.getDestinationTypeAsString(), destination.getPhysicalName());
        }
        super.removeDestination(context, destination, timeout);
    }

};