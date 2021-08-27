//,temp,LoggingBrokerPlugin.java,239,245,temp,LoggingBrokerPlugin.java,174,180
//,3
public class xxx {
    @Override
    public void removeConnection(ConnectionContext context, ConnectionInfo info, Throwable error) throws Exception {
        if (isLogAll() || isLogConnectionEvents()) {
            LOG.info("Removing Connection: {}", info);
        }
        super.removeConnection(context, info, error);
    }

};