//,temp,Stomp11Test.java,401,443,temp,StompTest.java,2196,2234
//,3
public class xxx {
    @Test(timeout = 60000)
    public void testSubscribeAndUnsubscribe() throws Exception {

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

        String message = "SEND\n" + "destination:/queue/" + getQueueName() + "\n\n" + "Hello World" + Stomp.NULL;

        stompConnection.sendFrame(message);

        String frame = "SUBSCRIBE\n" + "destination:/queue/" + getQueueName() + "\n" +
                       "id:12345\n" + "ack:auto\n\n" + Stomp.NULL;
        stompConnection.sendFrame(frame);

        StompFrame stompFrame = stompConnection.receive();
        assertTrue(stompFrame.getAction().equals("MESSAGE"));

        frame = "UNSUBSCRIBE\n" + "destination:/queue/" + getQueueName() + "\n" +
                "receipt:1\n" + "id:12345\n\n" + Stomp.NULL;
        stompConnection.sendFrame(frame);

        stompFrame = stompConnection.receive();
        assertTrue(stompFrame.getAction().equals("RECEIPT"));

        stompConnection.sendFrame(message);

        try {
            frame = stompConnection.receiveFrame(2000);
            LOG.info("Received frame: " + frame);
            fail("No message should have been received since subscription was removed");
        } catch (SocketTimeoutException e) {
        }
    }

};