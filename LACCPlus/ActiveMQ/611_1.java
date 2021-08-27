//,temp,StompAdvisoryTest.java,164,199,temp,StompAdvisoryTest.java,127,162
//,2
public class xxx {
    @Test(timeout = 60000)
    public void testConnectionAdvisoryXML() throws Exception {
        stompConnect();

        HashMap<String, String> subheaders = new HashMap<String, String>(1);
        subheaders.put("transformation", Stomp.Transformations.JMS_XML.toString());
        subheaders.put("receipt", "id-1");

        stompConnection.connect("system", "manager");
        stompConnection.subscribe("/topic/ActiveMQ.Advisory.Connection",
                Stomp.Headers.Subscribe.AckModeValues.AUTO, subheaders);

        String frame = stompConnection.receiveFrame();
        LOG.debug("Response to subscribe was: {}", frame);
        assertTrue(frame.trim().startsWith("RECEIPT"));

        // Now connect via openwire and check we get the advisory
        Connection c = cf.createConnection("system", "manager");
        c.start();

        StompFrame f = stompConnection.receive();
        LOG.debug(f.toString());
        assertEquals(f.getAction(),"MESSAGE");
        assertTrue("Should have a body", f.getBody().length() > 0);
        assertTrue(f.getBody().startsWith("<ConnectionInfo>"));

        c.stop();
        c.close();

        f = stompConnection.receive();
        LOG.debug(f.toString());
        assertEquals(f.getAction(),"MESSAGE");
        assertNotNull("Body is not null", f.getBody());
        assertTrue("Body should have content", f.getBody().length() > 0);
        assertTrue(f.getBody().startsWith("<ConnectionInfo>"));
    }

};