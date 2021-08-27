//,temp,LoggingBrokerPlugin.java,477,483,temp,LoggingBrokerPlugin.java,401,407
//,3
public class xxx {
    @Override
    public void removeDestinationInfo(ConnectionContext context, DestinationInfo info) throws Exception {
        if (isLogAll() || isLogInternalEvents()) {
            LOG.info("Removing destination info: {}", info);
        }
        super.removeDestinationInfo(context, info);
    }

};