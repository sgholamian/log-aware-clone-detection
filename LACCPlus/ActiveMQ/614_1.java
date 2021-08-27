//,temp,StompCompositeDestinationTest.java,150,193,temp,StompCompositeDestinationTest.java,105,148
//,2
public class xxx {
    @Test(timeout = 20000)
    public void testSubscribeToCompositeTopics() throws Exception {
        stompConnect();

        String destinationA = "StompA";
        String destinationB = "StompB";

        String frame = "CONNECT\n" +
                       "login:system\n" +
                       "passcode:manager\n\n" + Stomp.NULL;
        stompConnection.sendFrame(frame);

        frame = stompConnection.receiveFrame();
        assertTrue(frame.startsWith("CONNECTED"));

        LOG.info("Subscribing to destination: {},{}", destinationA, destinationB);

        frame = "SUBSCRIBE\n" +
                "destination:/topic/" + destinationA + ",/topic/" + destinationB + "\n" +
                "ack:auto\n\n" + Stomp.NULL;
        stompConnection.sendFrame(frame);

        // Test in same order as the subscribe command

        sendMessage(destinationA, true);
        sendMessage(destinationB, true);

        frame = stompConnection.receiveFrame();
        assertTrue(frame.startsWith("MESSAGE"));
        frame = stompConnection.receiveFrame();
        assertTrue(frame.startsWith("MESSAGE"));

        // Test the reverse ordering

        sendMessage(destinationB, true);
        sendMessage(destinationA, true);

        frame = stompConnection.receiveFrame();
        assertTrue(frame.startsWith("MESSAGE"));
        frame = stompConnection.receiveFrame();
        assertTrue(frame.startsWith("MESSAGE"));

        stompConnection.disconnect();
    }

};