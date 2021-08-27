//,temp,SjmsProducer.java,289,315,temp,JmsProducer.java,220,251
//,3
public class xxx {
            public Message createMessage(Session session) throws JMSException {
                Message answer = endpoint.getBinding().makeJmsMessage(exchange, in, session, null);

                Destination replyTo;
                String replyToOverride = endpoint.getReplyToOverride();
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

                String correlationId = determineCorrelationId(answer);
                replyManager.registerReply(replyManager, exchange, callback, originalCorrelationId, correlationId, timeout);

                if (LOG.isDebugEnabled()) {
                    LOG.debug("Using {}: {}, JMSReplyTo destination: {}, with request timeout: {} ms.",
                            "JMSCorrelationID", correlationId, replyTo, timeout);
                }

                LOG.trace("Created javax.jms.Message: {}", answer);
                return answer;
            }

};