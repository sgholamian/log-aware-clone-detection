//,temp,XmppConsumer.java,154,164,temp,XmppGroupChatProducer.java,91,98
//,3
public class xxx {
    private void checkConnection() throws Exception {
        if (!connection.isConnected()) {
            LOG.info("Attempting to reconnect to: {}", XmppEndpoint.getConnectionMessage(connection));
            try {
                connection.connect();
                LOG.debug("Successfully connected to XMPP server through: {}", connection);
            } catch (SmackException e) {
                LOG.warn("Connection to XMPP server failed. Will try to reconnect later again.", e);
            }
        }
    }

};