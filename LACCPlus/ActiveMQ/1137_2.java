//,temp,LoggingBrokerPlugin.java,385,391,temp,LoggingBrokerPlugin.java,377,383
//,2
public class xxx {
    @Override
    public void addSession(ConnectionContext context, SessionInfo info) throws Exception {
        if (isLogAll() || isLogSessionEvents()) {
            LOG.info("Adding Session: {}", info);
        }
        super.addSession(context, info);
    }

};