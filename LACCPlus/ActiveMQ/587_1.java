//,temp,StompTelnetTest.java,53,70,temp,StompTelnetTest.java,34,51
//,2
public class xxx {
    @Test(timeout = 60000)
    public void testCRLF11() throws Exception {

        for (TransportConnector connector : brokerService.getTransportConnectors()) {
            LOG.info("try: " + connector.getConnectUri());
            int port = connector.getConnectUri().getPort();

            StompConnection stompConnection = new StompConnection();
            stompConnection.open(createSocket(port));
            String frame = "CONNECT\r\naccept-version:1.1\r\n\r\n" + Stomp.NULL;
            stompConnection.sendFrame(frame);

            frame = stompConnection.receiveFrame();
            LOG.info("response from: " + connector.getConnectUri() + ", " + frame);
            assertTrue(frame.startsWith("CONNECTED"));
            stompConnection.close();
        }
    }

};