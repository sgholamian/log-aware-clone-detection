//,temp,SjmsProducer.java,345,411,temp,JmsProducer.java,323,389
//,3
public class xxx {
            public Message createMessage(Session session) throws JMSException {
                Message answer = endpoint.getBinding().makeJmsMessage(exchange, in, session, null);

                // when in InOnly mode the JMSReplyTo is a bit complicated
                // we only want to set the JMSReplyTo on the answer if
                // there is a JMSReplyTo from the header/endpoint and
                // we have been told to preserveMessageQos

                Object jmsReplyTo = JmsMessageHelper.getJMSReplyTo(answer);
                if (endpoint.isDisableReplyTo()) {
                    // honor disable reply to configuration
                    LOG.trace("ReplyTo is disabled on endpoint: {}", endpoint);
                    JmsMessageHelper.setJMSReplyTo(answer, null);
                } else {
                    // if the binding did not create the reply to then we have to try to create it here
                    if (jmsReplyTo == null) {
                        // prefer reply to from header over endpoint configured
                        jmsReplyTo = exchange.getIn().getHeader("JMSReplyTo", String.class);
                        if (jmsReplyTo == null) {
                            jmsReplyTo = endpoint.getReplyTo();
                        }
                    }
                }

                // we must honor these special flags to preserve QoS
                // as we are not OUT capable and thus do not expect a reply, and therefore
                // the consumer of this message should not return a reply so we remove it
                // unless we use preserveMessageQos=true to tell that we still want to use JMSReplyTo
                if (jmsReplyTo != null && !(endpoint.isPreserveMessageQos() || endpoint.isExplicitQosEnabled())) {
                    // log at debug what we are doing, as higher level may cause noise in production logs
                    // this behavior is also documented at the camel website
                    if (LOG.isDebugEnabled()) {
                        LOG.debug(
                                "Disabling JMSReplyTo: {} for destination: {}. Use preserveMessageQos=true to force Camel to keep the JMSReplyTo on endpoint: {}",
                                new Object[] { jmsReplyTo, to, endpoint });
                    }
                    jmsReplyTo = null;
                }

                // the reply to is a String, so we need to look up its Destination instance
                // and if needed create the destination using the session if needed to
                if (jmsReplyTo instanceof String) {
                    String replyTo = (String) jmsReplyTo;
                    // we need to null it as we use the String to resolve it as a Destination instance
                    jmsReplyTo = resolveOrCreateDestination(replyTo, session);
                }

                // set the JMSReplyTo on the answer if we are to use it
                Destination replyTo = null;
                String replyToOverride = endpoint.getReplyToOverride();
                if (replyToOverride != null) {
                    replyTo = resolveOrCreateDestination(replyToOverride, session);
                } else if (jmsReplyTo != null) {
                    replyTo = (Destination) jmsReplyTo;
                }
                if (replyTo != null) {
                    LOG.debug("Using JMSReplyTo destination: {}", replyTo);
                    JmsMessageHelper.setJMSReplyTo(answer, replyTo);
                } else {
                    // do not use JMSReplyTo
                    LOG.trace("Not using JMSReplyTo");
                    JmsMessageHelper.setJMSReplyTo(answer, null);
                }

                LOG.trace("Created javax.jms.Message: {}", answer);
                return answer;
            }

};