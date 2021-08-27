//,temp,Stomp11Test.java,535,575,temp,Stomp11Test.java,499,533
//,3
public class xxx {
    @Test(timeout = 60000)
    public void testAckMessageWithId() throws Exception {

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
                       "id:12345\n" + "ack:client\n\n" + Stomp.NULL;
        stompConnection.sendFrame(frame);

        StompFrame received = stompConnection.receive();
        assertTrue(received.getAction().equals("MESSAGE"));

        frame = "ACK\n" + "subscription:12345\n" + "message-id:" +
                received.getHeaders().get("message-id") + "\n\n" + Stomp.NULL;
        stompConnection.sendFrame(frame);

        frame = "UNSUBSCRIBE\n" + "destination:/queue/" + getQueueName() + "\n" +
                "id:12345\n\n" + Stomp.NULL;
        stompConnection.sendFrame(frame);
    }

};