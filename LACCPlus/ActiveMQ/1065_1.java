//,temp,StompAdvisoryTest.java,455,496,temp,StompAdvisoryTest.java,201,234
//,3
public class xxx {
    @Test(timeout = 60000)
    public void testDestinationAdvisoryCompositeTempDestinations() throws Exception {

        cf.setWatchTopicAdvisories(true);

        HashMap<String, String> subheaders = new HashMap<String, String>(1);
        subheaders.put("receipt", "id-1");

        stompConnect();
        stompConnection.connect("system", "manager");
        stompConnection.subscribe("/topic/ActiveMQ.Advisory.TempTopic,/topic/ActiveMQ.Advisory.TempQueue",
            Stomp.Headers.Subscribe.AckModeValues.AUTO, subheaders);

        String frame = stompConnection.receiveFrame();
        LOG.debug("Response to subscribe was: {}", frame);
        assertTrue(frame.trim().startsWith("RECEIPT"));

        // Now connect via openwire and check we get the advisory
        Connection connection = cf.createConnection("system", "manager");
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        session.createTemporaryTopic();
        session.createTemporaryQueue();

        ObjectName[] topicSubscribers = brokerService.getAdminView().getTopicSubscribers();
        for (ObjectName subscription : topicSubscribers) {
            LOG.info("Topic Subscription: {}", subscription);
        }

        connection.close();

        StompFrame f = stompConnection.receive();
        LOG.debug(f.toString());
        assertEquals(f.getAction(),"MESSAGE");
        assertTrue("Should have a body", f.getBody().length() > 0);
        assertTrue(f.getBody().startsWith("{\"DestinationInfo\":"));

        f = stompConnection.receive();
        LOG.debug(f.toString());
        assertEquals(f.getAction(),"MESSAGE");
        assertTrue("Should have a body", f.getBody().length() > 0);
        assertTrue(f.getBody().startsWith("{\"DestinationInfo\":"));
    }

};