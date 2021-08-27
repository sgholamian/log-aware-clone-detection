//,temp,SimpleJmsQueueConnector.java,449,485,temp,SimpleJmsTopicConnector.java,448,484
//,2
public class xxx {
    protected Topic createForeignTopic(TopicSession session, String topicName) throws JMSException {
        Topic result = null;

        if (preferJndiDestinationLookup) {
            try {
                // look-up the Queue
                result = jndiOutboundTemplate.lookup(topicName, Topic.class);
            } catch (NamingException e) {
                try {
                    result = session.createTopic(topicName);
                } catch (JMSException e1) {
                    String errStr = "Failed to look-up or create Topic for name: " + topicName;
                    LOG.error(errStr, e);
                    JMSException jmsEx = new JMSException(errStr);
                    jmsEx.setLinkedException(e1);
                    throw jmsEx;
                }
            }
        } else {
            try {
                result = session.createTopic(topicName);
            } catch (JMSException e) {
                // look-up the Topic
                try {
                    result = jndiOutboundTemplate.lookup(topicName, Topic.class);
                } catch (NamingException e1) {
                    String errStr = "Failed to look-up Topic for name: " + topicName;
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