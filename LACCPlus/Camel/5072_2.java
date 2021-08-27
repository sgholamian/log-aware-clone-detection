//,temp,EndpointMessageListener.java,311,353,temp,EndpointMessageListener.java,267,309
//,3
public class xxx {
    protected void sendReply(
            Session session,
            Destination replyDestination, final Message message, final Exchange exchange,
            final org.apache.camel.Message out, final Exception cause) {
        if (replyDestination == null) {
            LOG.debug("Cannot send reply message as there is no replyDestination for: {}", out);
            return;
        }
        try {
            SessionCallback callback = new SessionCallback() {
                @Override
                public Object doInJms(Session session) throws Exception {
                    MessageProducer producer = null;
                    try {
                        Message reply = endpoint.getBinding().makeJmsMessage(exchange, out, session, cause);
                        final String correlationID = determineCorrelationId(message);
                        reply.setJMSCorrelationID(correlationID);

                        if (LOG.isDebugEnabled()) {
                            LOG.debug("{} sending reply JMS message [correlationId:{}]: {}", endpoint, correlationID, reply);
                        }

                        producer = endpoint.getJmsObjectFactory().createMessageProducer(session, endpoint, replyDestination);
                        template.send(producer, reply);
                    } finally {
                        close(producer);
                    }

                    return null;
                }

                @Override
                public void onClose(Connection connection, Session session) {
                    // do not close as we use provided session
                }
            };

            getTemplate().execute(session, callback);

        } catch (Exception e) {
            exchange.setException(new CamelExchangeException("Unable to send reply JMS message", exchange, e));
        }
    }

};