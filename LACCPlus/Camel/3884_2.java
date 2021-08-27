//,temp,XmppPrivateChatProducer.java,112,128,temp,XmppGroupChatProducer.java,100,120
//,3
public class xxx {
    @Override
    protected void doStart() throws Exception {
        if (connection == null) {
            try {
                connection = endpoint.createConnection();
            } catch (SmackException e) {
                if (endpoint.isTestConnectionOnStartup()) {
                    throw new RuntimeException("Could not connect to XMPP server:  " + endpoint.getConnectionDescription(), e);
                } else {
                    LOG.warn("Could not connect to XMPP server. {}  Producer will attempt lazy connection when needed.",
                            e.getMessage());
                }
            }
        }

        if (chat == null && connection != null) {
            initializeChat();
        }

        super.doStart();
    }

};