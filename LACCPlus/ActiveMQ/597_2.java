//,temp,JmsTopicSendReceiveWithEmbeddedBrokerAndUserIDTest.java,90,102,temp,JmsTopicSendReceiveWithEmbeddedBrokerAndUserIDTest.java,76,88
//,2
public class xxx {
    public void testSpoofedJMSXUserIdIsIgnored() throws Exception {
        for (int i = 0; i < data.length; i++) {
            Message message = createMessage(i);
            configureMessage(message);
            message.setStringProperty("JMSXUserID", "spoofedId");
            if (verbose) {
                LOG.info("About to send a message: " + message + " with text: " + data[i]);
            }
            sendMessage(i, message);
        }
        assertMessagesAreReceived();
        LOG.info("" + data.length + " messages(s) received, closing down connections");
    }

};