//,temp,Stomp11Test.java,577,610,temp,Stomp11Test.java,469,497
//,3
public class xxx {
    @Test(timeout = 60000)
    public void testSubscribeWithWildcardSubscription() throws Exception {
        String connectFrame = "STOMP\n" +
                "login:system\n" +
                "passcode:manager\n" +
                "accept-version:1.1\n" +
                "host:localhost\n" +
                "\n" + Stomp.NULL;
        stompConnection.sendFrame(connectFrame);

        String f = stompConnection.receiveFrame();
        LOG.debug("Broker sent: " + f);

        assertTrue(f.startsWith("CONNECTED"));

        String message = "SEND\n" + "destination:/queue/a.b.c"  +
                "\n\n" + "Hello World" + Stomp.NULL;
        stompConnection.sendFrame(message);

        message = "SEND\n" + "destination:/queue/a.b"  +
                "\n\n" + "Hello World" + Stomp.NULL;
        stompConnection.sendFrame(message);


        String frame = "SUBSCRIBE\n" + "destination:/queue/a.b.>"  + "\n" +
                "id:12345\n" + "ack:auto\n\n" + Stomp.NULL;
        stompConnection.sendFrame(frame);

        StompFrame received = stompConnection.receive();
        assertNotNull(received);

        received = stompConnection.receive();
        assertNotNull(received);
    }

};