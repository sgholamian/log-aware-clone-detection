//,temp,Stomp11Test.java,127,144,temp,Stomp11Test.java,100,125
//,3
public class xxx {
    @Test(timeout = 60000)
    public void testConnectWithVersionOptions() throws Exception {

        String connectFrame = "STOMP\n" +
                              "login:system\n" +
                              "passcode:manager\n" +
                              "accept-version:1.0,1.1\n" +
                              "host:localhost\n" +
                              "\n" + Stomp.NULL;
        stompConnection.sendFrame(connectFrame);

        String f = stompConnection.receiveFrame();
        LOG.debug("Broker sent: " + f);

        assertTrue(f.startsWith("CONNECTED"));
        assertTrue(f.indexOf("version:1.1") >= 0);
        assertTrue(f.indexOf("session:") >= 0);
    }

};