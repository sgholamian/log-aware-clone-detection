//,temp,XmppConsumer.java,154,164,temp,XmppGroupChatProducer.java,91,98
//,3
public class xxx {
    private synchronized void reconnect() throws InterruptedException, IOException, SmackException, XMPPException {
        if (!connection.isConnected()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Reconnecting to: {}", XmppEndpoint.getConnectionMessage(connection));
            }
            connection.connect();
        }
    }

};