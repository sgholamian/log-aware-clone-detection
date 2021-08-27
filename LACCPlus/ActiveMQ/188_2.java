//,temp,LoggingBrokerPlugin.java,409,425,temp,LoggingBrokerPlugin.java,306,323
//,3
public class xxx {
    @Override
    public Connection[] getClients() throws Exception {
        Connection[] result = super.getClients();

        if (isLogAll() || isLogInternalEvents()) {
            if (result == null) {
                LOG.info("Get Clients returned empty list.");
            } else {
                StringBuffer cids = new StringBuffer();
                for (Connection c : result) {
                    cids.append(cids.length() > 0 ? ", " : "");
                    cids.append(c.getConnectionId());
                }
                LOG.info("Connected clients: {}", cids);
            }
        }
        return super.getClients();
    }

};