//,temp,JmsMessage.java,140,149,temp,SjmsMessage.java,142,151
//,2
public class xxx {
    public void setJmsMessage(Message jmsMessage) {
        if (jmsMessage != null) {
            try {
                setMessageId(jmsMessage.getJMSMessageID());
            } catch (JMSException e) {
                LOG.warn("Unable to retrieve JMSMessageID from JMS Message", e);
            }
        }
        this.jmsMessage = jmsMessage;
    }

};