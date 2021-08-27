//,temp,MQTTTest.java,1530,1563,temp,MQTTTest.java,1399,1427
//,3
public class xxx {
    @Test(timeout = 30 * 10000)
    public void testSubscribeWithZeroLengthTopic() throws Exception {

        MQTT mqtt = createMQTTConnection();
        mqtt.setClientId("MQTT-Client");
        mqtt.setCleanSession(false);

        Topic topic = new Topic("", QoS.EXACTLY_ONCE);

        final BlockingConnection connection = mqtt.blockingConnection();
        connection.connect();

        LOG.info("Trying to subscrobe to topic: {}", topic.name());

        try {
            connection.subscribe(new Topic[] { topic });
            fail("Should not be able to subscribe with invalid Topic");
        } catch (Exception ex) {
            LOG.info("Caught expected error on subscribe");
        }

        assertTrue("Should have lost connection", Wait.waitFor(new Wait.Condition() {

            @Override
            public boolean isSatisified() throws Exception {
                return !connection.isConnected();
            }
        }));
    }

};