//,temp,StompTest.java,1400,1428,temp,StompTest.java,1192,1229
//,3
public class xxx {
    @Test(timeout = 60000)
    public void testTransformationReceiveXMLMap() throws Exception {

        MessageProducer producer = session.createProducer(new ActiveMQQueue("USERS." + getQueueName()));
        MapMessage message = session.createMapMessage();
        message.setString("name", "Dejan");
        message.setString("city", "Belgrade");
        producer.send(message);

        String frame = "CONNECT\n" + "login:system\n" + "passcode:manager\n\n" + Stomp.NULL;
        stompConnection.sendFrame(frame);

        frame = stompConnection.receiveFrame();
        assertTrue(frame.startsWith("CONNECTED"));

        frame = "SUBSCRIBE\n" + "destination:/queue/USERS." + getQueueName() + "\n" + "ack:auto\n" + "transformation:" + Stomp.Transformations.JMS_MAP_XML + "\n\n" + Stomp.NULL;
        stompConnection.sendFrame(frame);

        StompFrame xmlFrame = stompConnection.receive();
        LOG.info("Received Frame: {}", xmlFrame.getBody());

        Map<String, String> map = createMapFromXml(xmlFrame.getBody());

        assertTrue(map.containsKey("name"));
        assertTrue(map.containsKey("city"));

        assertEquals("Dejan", map.get("name"));
        assertEquals("Belgrade", map.get("city"));
    }

};