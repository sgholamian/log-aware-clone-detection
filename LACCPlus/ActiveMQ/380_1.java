//,temp,Stomp11Test.java,814,852,temp,Stomp11Test.java,761,812
//,3
public class xxx {
    @Test(timeout = 60000)
    public void testHeaderValuesAreNotWSTrimmed() throws Exception {
        stompConnection.setVersion(Stomp.V1_1);
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

        String message = "SEND\n" + "destination:/queue/" + getQueueName() +
                         "\ntest1: value" +
                         "\ntest2:value " +
                         "\ntest3: value " +
                         "\n\n" + "Hello World" + Stomp.NULL;

        stompConnection.sendFrame(message);

        String frame = "SUBSCRIBE\n" + "destination:/queue/" + getQueueName() + "\n" +
                       "id:12345\n" + "ack:auto\n\n" + Stomp.NULL;
        stompConnection.sendFrame(frame);

        StompFrame received = stompConnection.receive();
        assertTrue(received.getAction().equals("MESSAGE"));

        assertEquals(" value", received.getHeaders().get("test1"));
        assertEquals("value ", received.getHeaders().get("test2"));
        assertEquals(" value ", received.getHeaders().get("test3"));

        frame = "UNSUBSCRIBE\n" + "destination:/queue/" + getQueueName() + "\n" +
                "id:12345\n\n" + Stomp.NULL;
        stompConnection.sendFrame(frame);
    }

};