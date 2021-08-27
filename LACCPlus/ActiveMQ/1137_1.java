//,temp,LoggingBrokerPlugin.java,385,391,temp,LoggingBrokerPlugin.java,377,383
//,2
public class xxx {
    @Override
    public void removeSession(ConnectionContext context, SessionInfo info) throws Exception {
        if (isLogAll() || isLogSessionEvents()) {
            LOG.info("Removing Session: {}", info);
        }
        super.removeSession(context, info);
    }

};