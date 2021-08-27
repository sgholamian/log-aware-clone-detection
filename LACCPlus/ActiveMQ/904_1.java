//,temp,Stomp11Test.java,361,379,temp,Stomp12Test.java,733,754
//,3
public class xxx {
    @Test(timeout = 60000)
    public void testRejectInvalidHeartbeats2() throws Exception {

        String connectFrame = "STOMP\n" +
                              "login:system\n" +
                              "passcode:manager\n" +
                              "accept-version:1.1\n" +
                              "heart-beat:T,0\n" +
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