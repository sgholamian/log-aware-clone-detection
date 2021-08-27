//,temp,TopicBridgeSpringTest.java,81,89,temp,QueueBridgeTest.java,88,95
//,3
public class xxx {
    public void testQueueRequestorOverBridge() throws JMSException {
        for (int i = 0; i < MESSAGE_COUNT; i++) {
            TextMessage msg = requestServerSession.createTextMessage("test msg: " + i);
            TextMessage result = (TextMessage)requestor.request(msg);
            assertNotNull(result);
            LOG.info(result.getText());
        }
    }

};