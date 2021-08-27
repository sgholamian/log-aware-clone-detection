//,temp,LoggingBrokerPlugin.java,239,245,temp,LoggingBrokerPlugin.java,174,180
//,3
public class xxx {
    @Override
    public void addConnection(ConnectionContext context, ConnectionInfo info) throws Exception {
        if (isLogAll() || isLogConnectionEvents()) {
            LOG.info("Adding Connection: {}", info);
        }
        super.addConnection(context, info);
    }

};