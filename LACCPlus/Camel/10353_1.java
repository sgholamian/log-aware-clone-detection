//,temp,SjmsProducer.java,430,441,temp,JmsProducer.java,465,476
//,2
public class xxx {
    protected void setMessageId(Exchange exchange) {
        if (exchange.hasOut()) {
            SjmsMessage out = exchange.getOut(SjmsMessage.class);
            try {
                if (out != null && out.getJmsMessage() != null) {
                    out.setMessageId(out.getJmsMessage().getJMSMessageID());
                }
            } catch (JMSException e) {
                LOG.warn("Unable to retrieve JMSMessageID from outgoing JMS Message and set it into Camel's MessageId", e);
            }
        }
    }

};