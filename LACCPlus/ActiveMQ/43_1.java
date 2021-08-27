//,temp,LoggingBrokerPlugin.java,469,475,temp,LoggingBrokerPlugin.java,427,433
//,3
public class xxx {
    @Override
    public void addDestinationInfo(ConnectionContext context, DestinationInfo info) throws Exception {
        if (isLogAll() || isLogInternalEvents()) {
            LOG.info("Adding destination info: {}", info);
        }
        super.addDestinationInfo(context, info);
    }

};