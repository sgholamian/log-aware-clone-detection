//,temp,JMSClientTest.java,480,500,temp,RetroactiveConsumerWithMessageQueryTest.java,47,69
//,3
public class xxx {
    public void testConsumeAndReceiveInitialQueryBeforeUpdates() throws Exception {

        // lets some messages
        connection = createConnection();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        connection.start();

        MessageConsumer consumer = session.createConsumer(destination);
        MessageIdList listener = new MessageIdList();
        listener.setVerbose(true);
        consumer.setMessageListener(listener);

        MessageProducer producer = session.createProducer(destination);
        int updateMessageCount = messageCount - DummyMessageQuery.MESSAGE_COUNT;
        for (int i = 0; i < updateMessageCount; i++) {
            TextMessage message = session.createTextMessage("Update Message: " + i + " sent at: " + new Date());
            producer.send(message);
        }
        producer.close();
        LOG.info("Sent: " + updateMessageCount + " update messages");

        listener.assertMessagesReceived(messageCount);
    }

};