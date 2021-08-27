//,temp,MQTTTest.java,1530,1563,temp,MQTTTest.java,1399,1427
//,3
public class xxx {
    @Test(timeout = 30 * 10000)
    public void testUnsubscribeWithInvalidMultiLevelWildcards() throws Exception {

        MQTT mqtt = createMQTTConnection();
        mqtt.setClientId("MQTT-Client");
        mqtt.setCleanSession(false);

        Topic[] topics = { new Topic("#/Foo", QoS.EXACTLY_ONCE),
                           new Topic("#/Foo/#", QoS.EXACTLY_ONCE),
                           new Topic("Foo/#/Level", QoS.EXACTLY_ONCE),
                           new Topic("Foo/X#", QoS.EXACTLY_ONCE) };

        for (int i = 0; i < topics.length; ++i) {
            final BlockingConnection connection = mqtt.blockingConnection();
            connection.connect();

            LOG.info("Trying to subscrobe to topic: {}", topics[i].name());

            try {
                connection.unsubscribe(new String[] { topics[i].name().toString() });
                fail("Should not be able to unsubscribe with invalid Topic");
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
    }

};