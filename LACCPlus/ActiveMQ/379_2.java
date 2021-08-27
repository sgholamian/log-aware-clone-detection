//,temp,Stomp11Test.java,577,610,temp,Stomp11Test.java,469,497
//,3
public class xxx {
    @Test(timeout = 60000)
    public void testUnsubscribeWithNoId() throws Exception {

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

        String frame = "SUBSCRIBE\n" + "destination:/queue/" + getQueueName() + "\n" +
                       "receipt:1\n" + "id:12345\n" + "ack:auto\n\n" + Stomp.NULL;
        stompConnection.sendFrame(frame);

        frame = stompConnection.receiveFrame();
        assertTrue(frame.startsWith("RECEIPT"));

        frame = "UNSUBSCRIBE\n" + "destination:/queue/" + getQueueName() + "\n" + "\n\n" + Stomp.NULL;
        stompConnection.sendFrame(frame);

        frame = stompConnection.receiveFrame();
        assertTrue(frame.startsWith("ERROR"));
    }

};