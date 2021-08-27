//,temp,MQTTTest.java,1459,1492,temp,MQTTTest.java,1429,1457
//,3
public class xxx {
    @Test(timeout = 30 * 10000)
    public void testUnsubscribeWithZeroLengthTopic() throws Exception {

        MQTT mqtt = createMQTTConnection();
        mqtt.setClientId("MQTT-Client");
        mqtt.setCleanSession(false);

        Topic topic = new Topic("", QoS.EXACTLY_ONCE);

        final BlockingConnection connection = mqtt.blockingConnection();
        connection.connect();

        LOG.info("Trying to subscrobe to topic: {}", topic.name());

        try {
            connection.unsubscribe(new String[] { topic.name().toString() });
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