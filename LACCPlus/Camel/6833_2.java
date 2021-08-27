//,temp,SjmsProducer.java,289,315,temp,JmsProducer.java,220,251
//,3
public class xxx {
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