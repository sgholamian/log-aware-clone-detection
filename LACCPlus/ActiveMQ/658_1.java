//,temp,JmsTopicSendReceiveWithEmbeddedBrokerAndUserIDTest.java,65,74,temp,JmsTopicSendReceiveWithEmbeddedBrokerAndUserIDTest.java,48,58
//,3
public class xxx {
    protected void assertMessagesReceivedAreValid2(List<Message> receivedMessages) throws JMSException {
        super.assertMessagesReceivedAreValid(receivedMessages);

        // lets assert that the user ID is set
        for (Message message : receivedMessages) {
            String userID = (String) message.getObjectProperty("JMSXUserID");
            LOG.info("Received message with userID: " + userID);
            assertEquals("JMSXUserID header", userName, userID);
        }
    }

};