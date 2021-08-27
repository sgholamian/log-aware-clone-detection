//,temp,XmppPubSubProducer.java,39,77,temp,XmppDirectProducer.java,41,90
//,3
public class xxx {
    @Override
    public void process(Exchange exchange) throws Exception {
        try {
            if (connection == null) {
                connection = endpoint.createConnection();
            }

            // make sure we are connected
            if (!connection.isConnected()) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Reconnecting to: {}", XmppEndpoint.getConnectionMessage(connection));
                }
                connection.connect();
            }
        } catch (XMPPException e) {
            throw new RuntimeExchangeException(
                    "Cannot connect to XMPP Server: "
                                               + ((connection != null)
                                                       ? XmppEndpoint.getConnectionMessage(connection) : endpoint.getHost()),
                    exchange, e);
        }

        try {
            Object body = exchange.getIn().getBody(Object.class);
            if (body instanceof PubSub) {
                PubSub pubsubpacket = (PubSub) body;
                endpoint.getBinding().populateXmppStanza(pubsubpacket, exchange);
                exchange.getIn().setHeader(XmppConstants.DOC_HEADER, pubsubpacket);
                connection.sendStanza(pubsubpacket);
            } else {
                throw new Exception("Message does not contain a pubsub packet");
            }
        } catch (XMPPException xmppe) {
            throw new RuntimeExchangeException(
                    "Cannot send XMPP pubsub: from " + endpoint.getUser()
                                               + " to: " + XmppEndpoint.getConnectionMessage(connection),
                    exchange, xmppe);
        }
    }

};