//,temp,JmsTopicRequestReplyTest.java,54,100,temp,JMSClientRequestResponseTest.java,128,152
//,3
public class xxx {
    public void testSendAndReceive() throws Exception {
        clientConnection = createConnection();
        clientConnection.setClientID("ClientConnection:" + getSubject());

        Session session = clientConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        clientConnection.start();

        Destination replyDestination = createTemporaryDestination(session);

        // lets test the destination
        clientSideClientID = clientConnection.getClientID();

        // TODO
        // String value = ActiveMQDestination.getClientId((ActiveMQDestination)
        // replyDestination);
        // assertEquals("clientID from the temporary destination must be the
        // same", clientSideClientID, value);
        LOG.info("Both the clientID and destination clientID match properly: " + clientSideClientID);

        /* build queues */
        MessageProducer requestProducer = session.createProducer(requestDestination);
        MessageConsumer replyConsumer = session.createConsumer(replyDestination);

        /* build requestmessage */
        TextMessage requestMessage = session.createTextMessage("Olivier");
        requestMessage.setJMSReplyTo(replyDestination);
        requestProducer.send(requestMessage);

        LOG.info("Sent request.");
        LOG.info(requestMessage.toString());

        Message msg = replyConsumer.receive(5000);

        if (msg instanceof TextMessage) {
            TextMessage replyMessage = (TextMessage)msg;
            LOG.info("Received reply.");
            LOG.info(replyMessage.toString());
            assertEquals("Wrong message content", "Hello: Olivier", replyMessage.getText());
        } else {
            fail("Should have received a reply by now");
        }
        replyConsumer.close();
        deleteTemporaryDestination(replyDestination);

        assertEquals("Should not have had any failures: " + failures, 0, failures.size());
    }

};