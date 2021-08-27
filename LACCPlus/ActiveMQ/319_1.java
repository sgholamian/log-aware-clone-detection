//,temp,StompAdvisoryTest.java,393,422,temp,StompAdvisoryTest.java,236,270
//,3
public class xxx {
    @Test(timeout = 60000)
    public void testDestinationAdvisoryTempQueue() throws Exception {

        cf.setWatchTopicAdvisories(false);

        stompConnect();

        HashMap<String, String> subheaders = new HashMap<String, String>(1);
        subheaders.put("receipt", "id-1");

        stompConnection.connect("system", "manager");
        stompConnection.subscribe("/topic/ActiveMQ.Advisory.TempQueue",
            Stomp.Headers.Subscribe.AckModeValues.AUTO, subheaders);

        String frame = stompConnection.receiveFrame();
        LOG.debug("Response to subscribe was: {}", frame);
        assertTrue(frame.trim().startsWith("RECEIPT"));

        // Now connect via openwire and check we get the advisory
        Connection connection = cf.createConnection("system", "manager");
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        session.createTemporaryQueue();
        connection.close();

        StompFrame f = stompConnection.receive();
        LOG.debug(f.toString());
        assertEquals(f.getAction(),"MESSAGE");
        assertTrue("Should have a body", f.getBody().length() > 0);
        assertTrue(f.getBody().startsWith("{\"DestinationInfo\":"));
    }

};