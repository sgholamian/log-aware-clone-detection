//,temp,EndpointMessageListener.java,321,340,temp,EndpointMessageListener.java,277,296
//,2
public class xxx {
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

};