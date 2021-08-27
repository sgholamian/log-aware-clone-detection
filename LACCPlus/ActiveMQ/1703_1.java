//,temp,StompTest.java,1430,1458,temp,StompTest.java,1261,1290
//,3
public class xxx {
    @Test(timeout = 60000)
    public void testTransformationReceiveJSONMap() throws Exception {

        MessageProducer producer = session.createProducer(new ActiveMQQueue("USERS." + getQueueName()));
        MapMessage message = session.createMapMessage();
        message.setString("name", "Dejan");
        message.setString("city", "Belgrade");
        producer.send(message);

        String frame = "CONNECT\n" + "login:system\n" + "passcode:manager\n\n" + Stomp.NULL;
        stompConnection.sendFrame(frame);

        frame = stompConnection.receiveFrame();
        assertTrue(frame.startsWith("CONNECTED"));

        frame = "SUBSCRIBE\n" + "destination:/queue/USERS." + getQueueName() + "\n" + "ack:auto\n" + "transformation:" + Stomp.Transformations.JMS_MAP_JSON + "\n\n" + Stomp.NULL;
        stompConnection.sendFrame(frame);

        StompFrame json = stompConnection.receive();
        LOG.info("Received Frame: {}", json.getBody());
        assertNotNull(json);
        Map<String, String> map = createMapFromJson(json.getBody());

        assertTrue(map.containsKey("name"));
        assertTrue(map.containsKey("city"));

        assertEquals("Dejan", map.get("name"));
        assertEquals("Belgrade", map.get("city"));
    }

};