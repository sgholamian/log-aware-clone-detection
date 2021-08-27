//,temp,StompAdvisoryTest.java,424,453,temp,StompAdvisoryTest.java,309,344
//,3
public class xxx {
    @Test(timeout = 60000)
    public void testDestinationAdvisoryTempTopic() throws Exception {

        cf.setWatchTopicAdvisories(false);

        stompConnect();

        HashMap<String, String> subheaders = new HashMap<String, String>(1);
        subheaders.put("receipt", "id-1");

        stompConnection.connect("system", "manager");
        stompConnection.subscribe("/topic/ActiveMQ.Advisory.TempTopic",
            Stomp.Headers.Subscribe.AckModeValues.AUTO, subheaders);

        String frame = stompConnection.receiveFrame();
        LOG.debug("Response to subscribe was: {}", frame);
        assertTrue(frame.trim().startsWith("RECEIPT"));

        // Now connect via openwire and check we get the advisory
        Connection connection = cf.createConnection("system", "manager");
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        session.createTemporaryTopic();
        connection.close();

        StompFrame f = stompConnection.receive();
        LOG.debug(f.toString());
        assertEquals(f.getAction(),"MESSAGE");
        assertTrue("Should have a body", f.getBody().length() > 0);
        assertTrue(f.getBody().startsWith("{\"DestinationInfo\":"));
    }

};