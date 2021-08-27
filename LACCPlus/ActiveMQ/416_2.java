//,temp,MQTTTest.java,577,646,temp,MQTTAuthTest.java,134,197
//,3
public class xxx {
    @Test(timeout = 60 * 1000)
    public void testFailedSubscription() throws Exception {
        final String ANONYMOUS = "anonymous";

        MQTT mqtt = createMQTTConnection();
        mqtt.setClientId("foo");
        mqtt.setKeepAlive((short) 2);
        mqtt.setVersion("3.1.1");

        BlockingConnection connection = mqtt.blockingConnection();
        connection.connect();

        final String NAMED = "named";
        byte[] qos = connection.subscribe(new Topic[] { new Topic(NAMED, QoS.AT_MOST_ONCE), new Topic(ANONYMOUS, QoS.EXACTLY_ONCE) });
        assertEquals((byte) 0x80, qos[0]);
        assertEquals((byte) QoS.EXACTLY_ONCE.ordinal(), qos[1]);

        // validate the subscription by sending a retained message
        connection.publish(ANONYMOUS, ANONYMOUS.getBytes(), QoS.AT_MOST_ONCE, true);
        Message msg = connection.receive(1000, TimeUnit.MILLISECONDS);
        assertNotNull(msg);
        assertEquals(ANONYMOUS, new String(msg.getPayload()));
        msg.ack();

        connection.unsubscribe(new String[] { ANONYMOUS });
        qos = connection.subscribe(new Topic[] { new Topic(ANONYMOUS, QoS.AT_LEAST_ONCE) });
        assertEquals((byte) QoS.AT_LEAST_ONCE.ordinal(), qos[0]);

        msg = connection.receive(1000, TimeUnit.MILLISECONDS);
        assertNotNull(msg);
        assertEquals(ANONYMOUS, new String(msg.getPayload()));
        msg.ack();

        //delete retained message
        connection.publish(ANONYMOUS, "".getBytes(), QoS.AT_MOST_ONCE, true);

        // that delete retained message gets dispatched! Wonder if that is expected?
        // guess it is simpler if it is - it shows up on the assertNull:196 below on occasion
        msg = connection.receive(1000, TimeUnit.MILLISECONDS);
        assertNotNull(msg);
        assertEquals(ANONYMOUS, new String(msg.getTopic()));
        msg.ack();

        connection.disconnect();

        // Test 3.1 functionality
        mqtt.setVersion("3.1");
        connection = mqtt.blockingConnection();
        connection.connect();
        qos = connection.subscribe(new Topic[] { new Topic(NAMED, QoS.AT_MOST_ONCE) });
        assertEquals(QoS.AT_MOST_ONCE.ordinal(), qos[0]);

        MQTT mqttPub = createMQTTConnection("pub", true);
        mqttPub.setUserName("admin");
        mqttPub.setPassword("admin");

        BlockingConnection connectionPub = mqttPub.blockingConnection();
        connectionPub.connect();
        connectionPub.publish(NAMED, NAMED.getBytes(), QoS.AT_MOST_ONCE, true);

        msg = connection.receive(1000, TimeUnit.MILLISECONDS);
        LOG.info("got msg: " + msg + ", " + (msg != null ? new String(msg.getTopic()) : ""));
        assertNull(msg);
    }

};