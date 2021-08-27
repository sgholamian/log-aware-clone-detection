//,temp,StompTest.java,2065,2127,temp,StompTest.java,2010,2050
//,3
public class xxx {
    private void doReplyToAcrossConnections(String type) throws Exception {
        LOG.info("Starting test on Temp Destinations using a temporary: " + type);

        StompConnection responder = new StompConnection();
        stompConnect(responder);
        String frame = "CONNECT\n" + "login:system\n" + "passcode:manager\n\n" + Stomp.NULL;
        responder.sendFrame(frame);

        frame = responder.receiveFrame();
        assertTrue(frame.startsWith("CONNECTED"));

        final String dest = "/" + type + "/" + getQueueName();
        final String tempDest = String.format("/temp-%s/2C26441740C0ECC9tt1:1:0:1", type);
        LOG.info("Test is using out-bound topic: " + dest + ", and replyTo dest: " + tempDest);

        // Subscribe to the temp destination, this is where we get our response.
        stompConnection.subscribe(tempDest);

        // Subscribe to the destination, this is where we get our request.
        HashMap<String, String> properties = new HashMap<String, String>();
        properties.put(Stomp.Headers.RECEIPT_REQUESTED, "subscribe-1");
        responder.subscribe(dest, null, properties);

        frame = responder.receiveFrame();
        assertTrue("Receipt Frame: " + frame, frame.trim().startsWith("RECEIPT"));
        assertTrue("Receipt contains correct receipt-id " + frame, frame.indexOf(Stomp.Headers.Response.RECEIPT_ID) >= 0);

        // Send a Message with the ReplyTo value set.
        properties = new HashMap<String, String>();
        properties.put(Stomp.Headers.Send.REPLY_TO, tempDest);
        properties.put(Stomp.Headers.RECEIPT_REQUESTED, "send-1");
        LOG.info(String.format("Sending request message: SEND with %s=%s", Stomp.Headers.Send.REPLY_TO, tempDest));
        stompConnection.send(dest, "REQUEST", null, properties);

        frame = stompConnection.receiveFrame();
        assertTrue("Receipt Frame: " + frame, frame.trim().startsWith("RECEIPT"));
        assertTrue("Receipt contains correct receipt-id " + frame, frame.indexOf(Stomp.Headers.Response.RECEIPT_ID) >= 0);

        // The subscription should receive a response with the ReplyTo property set.
        StompFrame received = responder.receive();
        assertNotNull(received);
        String remoteReplyTo = received.getHeaders().get(Stomp.Headers.Send.REPLY_TO);
        assertNotNull(remoteReplyTo);
        assertTrue(remoteReplyTo.startsWith(String.format("/remote-temp-%s/", type)));
        LOG.info(String.format("Received request message: %s with %s=%s", received.getAction(), Stomp.Headers.Send.REPLY_TO, remoteReplyTo));

        // Reply to the request using the given ReplyTo destination
        responder.send(remoteReplyTo, "RESPONSE");

        // The response should be received by the Temporary Destination subscription
        StompFrame reply = stompConnection.receive();
        assertNotNull(reply);
        assertEquals("MESSAGE", reply.getAction());
        assertTrue(reply.getBody().contains("RESPONSE"));
        LOG.info(String.format("Response %s received", reply.getAction()));

        BrokerViewMBean broker = getProxyToBroker();
        if (type.equals("topic")) {
            assertEquals(1, broker.getTemporaryTopics().length);
        } else {
            assertEquals(1, broker.getTemporaryQueues().length);
        }
    }

};