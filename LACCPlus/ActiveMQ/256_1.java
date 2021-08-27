//,temp,Stomp11Test.java,445,467,temp,StompInactivityMonitorTest.java,60,92
//,3
public class xxx {
    @Test(timeout = 60000)
    public void testSubscribeWithNoId() throws Exception {

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
                       "ack:auto\n\n" + Stomp.NULL;
        stompConnection.sendFrame(frame);

        frame = stompConnection.receiveFrame();
        assertTrue(frame.startsWith("ERROR"));
    }

};