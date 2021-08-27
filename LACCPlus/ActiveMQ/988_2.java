//,temp,StompTest.java,1400,1428,temp,StompTest.java,1192,1229
//,3
public class xxx {
    @Test(timeout = 60000)
    public void testTransformationReceiveJSONObjectAndMap() throws Exception {
        MessageProducer producer = session.createProducer(new ActiveMQQueue("USERS." + getQueueName()));
        ObjectMessage objMessage = session.createObjectMessage(new SamplePojo("Dejan", "Belgrade"));
        producer.send(objMessage);

        MapMessage mapMessage = session.createMapMessage();
        mapMessage.setString("name", "Dejan");
        mapMessage.setString("city", "Belgrade");
        producer.send(mapMessage);

        String frame = "CONNECT\n" + "login:system\n" + "passcode:manager\n\n" + Stomp.NULL;
        stompConnection.sendFrame(frame);

        frame = stompConnection.receiveFrame();
        assertTrue(frame.startsWith("CONNECTED"));

        frame = "SUBSCRIBE\n" + "destination:/queue/USERS." + getQueueName() + "\n" + "ack:auto" + "\n" + "transformation:"	+ Stomp.Transformations.JMS_JSON + "\n\n" + Stomp.NULL;
        stompConnection.sendFrame(frame);

        StompFrame json = stompConnection.receive();
        LOG.info("Transformed frame: {}", json);

        SamplePojo pojo = createObjectFromJson(json.getBody());
        assertTrue(pojo.getCity().equals("Belgrade"));
        assertTrue(pojo.getName().equals("Dejan"));

        json = stompConnection.receive();
        LOG.info("Transformed frame: {}", json);

        Map<String, String> map = createMapFromJson(json.getBody());

        assertTrue(map.containsKey("name"));
        assertTrue(map.containsKey("city"));

        assertTrue(map.get("name").equals("Dejan"));
        assertTrue(map.get("city").equals("Belgrade"));
    }

};