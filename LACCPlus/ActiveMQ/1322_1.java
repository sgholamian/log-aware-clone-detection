//,temp,StompWSTransportTest.java,167,195,temp,StompWSTransportTest.java,137,165
//,2
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
        wsStompConnection.sendRawFrame(connectFrame);

        try {
            String incoming = wsStompConnection.receive(30, TimeUnit.SECONDS);

            assertTrue(incoming.startsWith("ERROR"));
            assertTrue(incoming.indexOf("heart-beat") >= 0);
            assertTrue(incoming.indexOf("message:") >= 0);
        } catch (IOException ex) {
            LOG.debug("Connection closed before Frame was read.");
        }

        assertTrue("Connection should close", Wait.waitFor(new Wait.Condition() {

            @Override
            public boolean isSatisified() throws Exception {
                return getProxyToBroker().getCurrentConnectionsCount() == 0;
            }
        }));
    }

};