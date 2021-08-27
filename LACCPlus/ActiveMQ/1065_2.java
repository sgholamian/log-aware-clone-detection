//,temp,StompAdvisoryTest.java,455,496,temp,StompAdvisoryTest.java,201,234
//,3
public class xxx {
    @Test(timeout = 60000)
    public void testConsumerAdvisory() throws Exception {
        stompConnect();

        Destination dest = new ActiveMQQueue("testConsumerAdvisory");

        HashMap<String, String> subheaders = new HashMap<String, String>(1);
        subheaders.put("receipt", "id-1");

        stompConnection.connect("system", "manager");
        stompConnection.subscribe("/topic/ActiveMQ.Advisory.Consumer.>",
            Stomp.Headers.Subscribe.AckModeValues.AUTO, subheaders);

        String frame = stompConnection.receiveFrame();
        LOG.debug("Response to subscribe was: {}", frame);
        assertTrue(frame.trim().startsWith("RECEIPT"));

        // Now connect via openwire and check we get the advisory
        Connection c = cf.createConnection("system", "manager");
        c.start();

        Session session = c.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageConsumer consumer = session.createConsumer(dest);
        assertNotNull(consumer);

        StompFrame f = stompConnection.receive();
        LOG.debug(f.toString());
        assertEquals(f.getAction(),"MESSAGE");
        assertTrue("Should have a body", f.getBody().length() > 0);
        assertTrue(f.getBody().startsWith("{\"ConsumerInfo\":"));

        c.stop();
        c.close();
    }

};