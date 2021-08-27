//,temp,JmsTopicRequestReplyTest.java,54,100,temp,JMSClientRequestResponseTest.java,128,152
//,3
public class xxx {
    private void doTestRequestResponse() throws Exception {

        MessageProducer requestProducer = requestorSession.createProducer(requestDestination);
        MessageConsumer replyConsumer = requestorSession.createConsumer(replyDestination);

        TextMessage requestMessage = requestorSession.createTextMessage("SomeRequest");
        requestMessage.setJMSReplyTo(replyDestination);
        requestProducer.send(requestMessage);

        LOG.info("Sent request to destination: {}", requestDestination.toString());

        Message msg = replyConsumer.receive(10000);

        if (msg instanceof TextMessage) {
            TextMessage replyMessage = (TextMessage)msg;
            LOG.info("Received reply.");
            LOG.info(replyMessage.toString());
            assertTrue("Wrong message content", replyMessage.getText().startsWith("response"));
        } else {
            fail("Should have received a reply by now");
        }
        replyConsumer.close();

        assertEquals("Should not have had any failures: " + failures, 0, failures.size());
    }

};