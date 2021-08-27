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
            Object body = exchange.getIn().getBody();
            if (body instanceof Stanza) {
                connection.sendStanza((Stanza) body);

            } else if (body instanceof Stanza[]) {
                final Stanza[] packets = (Stanza[]) body;
                for (final Stanza packet : packets) {
                    connection.sendStanza(packet);
                }

            } else {
                throw new Exception("Body does not contain Stanza/Stanza[] object(s)");
            }
        } catch (XMPPException xmppe) {
            throw new RuntimeExchangeException(
                    "Cannot send XMPP direct: from " + endpoint.getUser() + " to: "
                                               + XmppEndpoint.getConnectionMessage(connection),
                    exchange, xmppe);

        } catch (Exception e) {
            throw new RuntimeExchangeException(
                    "Cannot send XMPP direct: from " + endpoint.getUser() + " to: "
                                               + XmppEndpoint.getConnectionMessage(connection),
                    exchange, e);
        }
    }

};