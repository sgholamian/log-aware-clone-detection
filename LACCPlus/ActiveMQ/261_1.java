//,temp,Stomp11Test.java,381,399,temp,Stomp11Test.java,165,182
//,3
public class xxx {
    @Test(timeout = 60000)
    public void testRejectInvalidHeartbeats3() throws Exception {

        String connectFrame = "STOMP\n" +
                              "login:system\n" +
                              "passcode:manager\n" +
                              "accept-version:1.1\n" +
                              "heart-beat:100,10,50\n" +
                              "host:localhost\n" +
                              "\n" + Stomp.NULL;
        stompConnection.sendFrame(connectFrame);

        String f = stompConnection.receiveFrame();
        LOG.debug("Broker sent: " + f);

        assertTrue(f.startsWith("ERROR"));
        assertTrue(f.indexOf("heart-beat") >= 0);
        assertTrue(f.indexOf("message:") >= 0);
    }

};