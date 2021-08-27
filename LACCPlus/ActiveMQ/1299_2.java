//,temp,JmsTopicCompositeSendReceiveTest.java,81,87,temp,JmsQueueTopicCompositeSendReceiveTest.java,81,87
//,2
public class xxx {
    public void testSendReceive() throws Exception {
        super.testSendReceive();
        messages.clear();
        consumer2.setMessageListener(this);
        assertMessagesAreReceived();
        LOG.info("" + data.length + " messages(s) received, closing down connections");
    }

};