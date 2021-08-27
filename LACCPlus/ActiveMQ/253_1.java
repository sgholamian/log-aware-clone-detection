//,temp,TopicBridgeSpringTest.java,81,89,temp,QueueBridgeTest.java,88,95
//,3
public class xxx {
    public void testTopicRequestorOverBridge() throws JMSException {
        for (int i = 0; i < MESSAGE_COUNT; i++) {
            TextMessage msg = requestServerSession.createTextMessage("test msg: " + i);
            LOG.info("Making request: " + msg);
            TextMessage result = (TextMessage)requestor.request(msg);
            assertNotNull(result);
            LOG.info("Received result: " + result.getText());
        }
    }

};