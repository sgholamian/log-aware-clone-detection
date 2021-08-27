//,temp,DeadLetterTestSupport.java,149,160,temp,AMQ3405Test.java,206,216
//,3
public class xxx {
    protected void sendMessages() throws JMSException {
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        producer = session.createProducer(getDestination());
        producer.setDeliveryMode(deliveryMode);

        LOG.info("Sending " + messageCount + " messages to: " + getDestination());
        for (int i = 0; i < messageCount; i++) {
            Message message = createMessage(session, i);
            producer.send(message);
        }
    }

};