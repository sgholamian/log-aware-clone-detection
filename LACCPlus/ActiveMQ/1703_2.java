//,temp,StompTest.java,1430,1458,temp,StompTest.java,1261,1290
//,3
public class xxx {
    @Test(timeout = 60000)
    public void testTransformationSendAndReceiveJsonMap() throws Exception {

        String frame = "CONNECT\n" + "login:system\n" + "passcode:manager\n\n" + Stomp.NULL;
        stompConnection.sendFrame(frame);

        frame = stompConnection.receiveFrame();
        assertTrue(frame.startsWith("CONNECTED"));

        frame = "SUBSCRIBE\n" + "destination:/queue/" + getQueueName() + "\n" + "ack:auto" + "\n" + "transformation:" + Stomp.Transformations.JMS_JSON + "\n\n" + Stomp.NULL;
        stompConnection.sendFrame(frame);

        frame = "SEND\n" + "destination:/queue/" + getQueueName() + "\n" + "transformation:" + Stomp.Transformations.JMS_MAP_XML + "\n\n" + xmlMap + Stomp.NULL;

        stompConnection.sendFrame(frame);

        StompFrame json = stompConnection.receive();
        LOG.info("Received Frame: {}", json.getBody());

        assertNotNull(json);
        assertTrue(json.getHeaders().containsValue("jms-map-json"));

        Map<String, String> map = createMapFromJson(json.getBody());

        assertTrue(map.containsKey("name"));
        assertTrue(map.containsKey("city"));

        assertEquals("Dejan", map.get("name"));
        assertEquals("Belgrade", map.get("city"));
    }

};