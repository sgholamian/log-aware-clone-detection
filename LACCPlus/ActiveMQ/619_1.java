//,temp,SimpleJmsQueueConnector.java,449,485,temp,SimpleJmsTopicConnector.java,448,484
//,2
public class xxx {
    protected Queue createForeignQueue(QueueSession session, String queueName) throws JMSException {
        Queue result = null;

        if (preferJndiDestinationLookup) {
            try {
                // look-up the Queue
                result = jndiOutboundTemplate.lookup(queueName, Queue.class);
            } catch (NamingException e) {
                try {
                    result = session.createQueue(queueName);
                } catch (JMSException e1) {
                    String errStr = "Failed to look-up or create Queue for name: " + queueName;
                    LOG.error(errStr, e);
                    JMSException jmsEx = new JMSException(errStr);
                    jmsEx.setLinkedException(e1);
                    throw jmsEx;
                }
            }
        } else {
            try {
                result = session.createQueue(queueName);
            } catch (JMSException e) {
                // look-up the Queue
                try {
                    result = jndiOutboundTemplate.lookup(queueName, Queue.class);
                } catch (NamingException e1) {
                    String errStr = "Failed to look-up Queue for name: " + queueName;
                    LOG.error(errStr, e);
                    JMSException jmsEx = new JMSException(errStr);
                    jmsEx.setLinkedException(e1);
                    throw jmsEx;
                }
            }
        }

        return result;
    }

};