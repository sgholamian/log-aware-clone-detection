//,temp,Stomp11Test.java,361,379,temp,Stomp12Test.java,733,754
//,3
public class xxx {
    @Test(timeout = 60000)
    public void testSubscribeWithNoId() throws Exception {
        String connectFrame = "STOMP\n" +
                              "login:system\n" +
                              "passcode:manager\n" +
                              "accept-version:1.2\n" +
                              "host:localhost\n" +
                              "\n" + Stomp.NULL;
        stompConnection.sendFrame(connectFrame);

        String f = stompConnection.receiveFrame();
        LOG.debug("Broker sent: " + f);

        assertTrue(f.startsWith("CONNECTED"));

        String frame = "SUBSCRIBE\n" + "destination:/queue/" + getQueueName() + "\n" +
                       "ack:auto\n\n" + Stomp.NULL;
        stompConnection.sendFrame(frame);

        frame = stompConnection.receiveFrame();
        assertTrue(frame.startsWith("ERROR"));
    }

};