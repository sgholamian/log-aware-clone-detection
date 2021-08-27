//,temp,SimpleNetworkTest.java,96,128,temp,ThreeBrokerTempDestDemandSubscriptionCleanupTest.java,208,239
//,3
public class xxx {
    @Test(timeout = 60 * 1000)
    public void testRequestReply() throws Exception {
        final MessageProducer remoteProducer = remoteSession.createProducer(null);
        MessageConsumer remoteConsumer = remoteSession.createConsumer(included);
        remoteConsumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message msg) {
                try {
                    TextMessage textMsg = (TextMessage)msg;
                    String payload = "REPLY: " + textMsg.getText();
                    Destination replyTo;
                    replyTo = msg.getJMSReplyTo();
                    textMsg.clearBody();
                    textMsg.setText(payload);
                    remoteProducer.send(replyTo, textMsg);
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });

        TopicRequestor requestor = new TopicRequestor((TopicSession)localSession, included);
        // allow for consumer infos to perculate arround
        Thread.sleep(5000);
        for (int i = 0; i < MESSAGE_COUNT; i++) {
            TextMessage msg = localSession.createTextMessage("test msg: " + i);
            TextMessage result = (TextMessage)requestor.request(msg);
            assertNotNull(result);
            LOG.info(result.getText());
        }

        assertNetworkBridgeStatistics(MESSAGE_COUNT, MESSAGE_COUNT);
    }

};