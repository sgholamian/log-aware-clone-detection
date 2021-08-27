//,temp,JmsMessage.java,246,263,temp,SjmsMessage.java,264,281
//,2
public class xxx {
    @Override
    protected String createMessageId() {
        if (jmsMessage == null) {
            LOG.trace("No javax.jms.Message set so generating a new message id");
            return super.createMessageId();
        }
        try {
            String id = getDestinationAsString(jmsMessage.getJMSDestination());
            if (id != null) {
                id += jmsMessage.getJMSMessageID();
            } else {
                id = jmsMessage.getJMSMessageID();
            }
            return getSanitizedString(id);
        } catch (JMSException e) {
            throw new RuntimeExchangeException("Unable to retrieve JMSMessageID from JMS Message", getExchange(), e);
        }
    }

};