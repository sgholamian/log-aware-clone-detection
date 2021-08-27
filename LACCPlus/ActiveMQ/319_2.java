//,temp,StompAdvisoryTest.java,393,422,temp,StompAdvisoryTest.java,236,270
//,3
public class xxx {
    @Test(timeout = 60000)
    public void testProducerAdvisory() throws Exception {
        stompConnect();

        Destination dest = new ActiveMQQueue("testProducerAdvisory");

        HashMap<String, String> subheaders = new HashMap<String, String>(1);
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