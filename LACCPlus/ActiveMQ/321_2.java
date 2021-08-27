//,temp,StompAdvisoryTest.java,424,453,temp,StompAdvisoryTest.java,309,344
//,3
public class xxx {
    @Test(timeout = 60000)
    public void testProducerAdvisoryJSON() throws Exception {
        stompConnect();

        Destination dest = new ActiveMQQueue("testProducerAdvisoryJSON");

        HashMap<String, String> subheaders = new HashMap<String, String>(1);
        subheaders.put("transformation", Stomp.Transformations.JMS_ADVISORY_JSON.toString());
        subheaders.put("receipt", "id-1");

        stompConnection.connect("system", "manager");
        stompConnection.subscribe("/topic/ActiveMQ.Advisory.Producer.>",
                Stomp.Headers.Subscribe.AckModeValues.AUTO, subheaders);

        String frame = stompConnection.receiveFrame();
        LOG.debug("Response to subscribe was: {}", frame);
        assertTrue(frame.trim().startsWith("RECEIPT"));

        // Now connect via openwire and check we get the advisory
        Connection c = cf.createConnection("system", "manager");
        c.start();

        Session session = c.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageProducer producer = session.createProducer(dest);
        Message mess = session.createTextMessage("test");
        producer.send(mess);

        StompFrame f = stompConnection.receive();
        LOG.debug(f.toString());
        assertEquals(f.getAction(),"MESSAGE");
        assertTrue("Should have a body", f.getBody().length() > 0);
        assertTrue(f.getBody().startsWith("{\"ProducerInfo\":"));

        c.stop();
        c.close();
    }

};