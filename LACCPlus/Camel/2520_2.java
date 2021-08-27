//,temp,SjmsProducer.java,263,328,temp,JmsProducer.java,169,258
//,3
public class xxx {
    protected boolean processInOut(final Exchange exchange, final AsyncCallback callback) {
        final org.apache.camel.Message in = exchange.getIn();

        String destinationName = in.getHeader(JmsConstants.JMS_DESTINATION_NAME, String.class);
        // remove the header so it wont be propagated
        in.removeHeader(JmsConstants.JMS_DESTINATION_NAME);
        if (destinationName == null) {
            destinationName = endpoint.getDestinationName();
        }

        Destination destination = in.getHeader(JmsConstants.JMS_DESTINATION, Destination.class);
        // remove the header so it wont be propagated
        in.removeHeader(JmsConstants.JMS_DESTINATION);
        if (destination != null) {
            // prefer to use destination over destination name
            destinationName = null;
        }

        initReplyManager();

        // the request timeout can be overruled by a header otherwise the endpoint configured value is used
        final long timeout
                = exchange.getIn().getHeader(JmsConstants.JMS_REQUEST_TIMEOUT, endpoint.getRequestTimeout(), long.class);

        final JmsConfiguration configuration = endpoint.getConfiguration();

        // when using message id as correlation id, we need at first to use a provisional correlation id
        // which we then update to the real JMSMessageID when the message has been sent
        // this is done with the help of the MessageSentCallback
        final boolean msgIdAsCorrId = configuration.isUseMessageIDAsCorrelationID();
        final String provisionalCorrelationId = msgIdAsCorrId ? getUuidGenerator().generateUuid() : null;
        MessageSentCallback messageSentCallback = null;
        if (msgIdAsCorrId) {
            messageSentCallback
                    = new UseMessageIdAsCorrelationIdMessageSentCallback(replyManager, provisionalCorrelationId, timeout);
        }

        final String correlationProperty = configuration.getCorrelationProperty();

        final String correlationPropertyToUse = ofNullable(correlationProperty).orElse("JMSCorrelationID");

        final String originalCorrelationId = in.getHeader(correlationPropertyToUse, String.class);

        boolean generateFreshCorrId = (ObjectHelper.isEmpty(originalCorrelationId) && !msgIdAsCorrId)
                || (originalCorrelationId != null && originalCorrelationId.startsWith(GENERATED_CORRELATION_ID_PREFIX));
        if (generateFreshCorrId) {
            // we append the 'Camel-' prefix to know it was generated by us
            in.setHeader(correlationPropertyToUse, GENERATED_CORRELATION_ID_PREFIX + getUuidGenerator().generateUuid());
        }

        MessageCreator messageCreator = new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                Message answer = endpoint.getBinding().makeJmsMessage(exchange, in, session, null);

                Destination replyTo = null;
                String replyToOverride = configuration.getReplyToOverride();
                if (replyToOverride != null) {
                    replyTo = resolveOrCreateDestination(replyToOverride, session);
                } else {
                    // get the reply to destination to be used from the reply manager
                    replyTo = replyManager.getReplyTo();
                }
                if (replyTo == null) {
                    throw new RuntimeExchangeException("Failed to resolve replyTo destination", exchange);
                }
                JmsMessageHelper.setJMSReplyTo(answer, replyTo);
                replyManager.setReplyToSelectorHeader(in, answer);

                String correlationId = determineCorrelationId(answer, provisionalCorrelationId);
                replyManager.registerReply(replyManager, exchange, callback, originalCorrelationId, correlationId, timeout);

                if (correlationProperty != null) {
                    replyManager.setCorrelationProperty(correlationProperty);
                }

                if (LOG.isDebugEnabled()) {
                    LOG.debug("Using {}: {}, JMSReplyTo destination: {}, with request timeout: {} ms.",
                            new Object[] { correlationPropertyToUse, correlationId, replyTo, timeout });
                }

                LOG.trace("Created javax.jms.Message: {}", answer);
                return answer;
            }
        };

        doSend(true, destinationName, destination, messageCreator, messageSentCallback);

        // continue routing asynchronously (reply will be processed async when its received)
        return false;
    }

};