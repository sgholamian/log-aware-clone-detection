//,temp,StompTest.java,2065,2127,temp,StompTest.java,2010,2050
//,3
public class xxx {
    private void doTestActiveMQReplyToTempDestination(String type) throws Exception {
        LOG.info("Starting test on Temp Destinations using a temporary: " + type);

        final String dest = "/" + type + "/" + getQueueName();
        final String tempDest = String.format("/temp-%s/2C26441740C0ECC9tt1", type);
        LOG.info("Test is using out-bound topic: " + dest + ", and replyTo dest: " + tempDest);

        // Subscribe both to the out-bound destination and the response tempt destination
        stompConnection.subscribe(dest);
        stompConnection.subscribe(tempDest);

        // Send a Message with the ReplyTo value set.
        HashMap<String, String> properties = new HashMap<String, String>();
        properties.put(Stomp.Headers.Send.REPLY_TO, tempDest);
        LOG.info(String.format("Sending request message: SEND with %s=%s", Stomp.Headers.Send.REPLY_TO, tempDest));
        stompConnection.send(dest, "REQUEST", null, properties);

        // The subscription should receive a response with the ReplyTo property set.
        StompFrame received = stompConnection.receive();
        assertNotNull(received);
        String remoteReplyTo = received.getHeaders().get(Stomp.Headers.Send.REPLY_TO);
        assertNotNull(remoteReplyTo);
        assertTrue(remoteReplyTo.startsWith(String.format("/temp-%s/", type)));
        LOG.info(String.format("Received request message: %s with %s=%s", received.getAction(), Stomp.Headers.Send.REPLY_TO, remoteReplyTo));

        // Reply to the request using the given ReplyTo destination
        stompConnection.send(remoteReplyTo, "RESPONSE");

        // The response should be received by the Temporary Destination subscription
        StompFrame reply = stompConnection.receive();
        assertNotNull(reply);
        assertEquals("MESSAGE", reply.getAction());
        LOG.info(String.format("Response %s received", reply.getAction()));

        BrokerViewMBean broker = getProxyToBroker();
        if (type.equals("topic")) {
            assertEquals(1, broker.getTemporaryTopics().length);
        } else {
            assertEquals(1, broker.getTemporaryQueues().length);
        }
    }

};