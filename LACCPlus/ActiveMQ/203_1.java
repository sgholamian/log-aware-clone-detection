//,temp,JmsQueueBrowserExpirationTest.java,173,189,temp,MemoryUsageBlockResumeTest.java,125,140
//,3
public class xxx {
    protected void sendTestMessages() throws JMSException {
        // Send the messages to the Queue.
        Connection prodConnection = factory.createConnection();
        prodConnection.start();
        Session prodSession = prodConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageProducer producer = prodSession.createProducer(queue);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        producer.setTimeToLive(TTL);

        for (int i = 1; i <= MESSAGES_TO_SEND; i++) {
            String msgStr = "Message: " + i;
            producer.send(prodSession.createTextMessage(msgStr));
            LOG.info("P&C: {}", msgStr);
        }

        prodSession.close();
    }

};