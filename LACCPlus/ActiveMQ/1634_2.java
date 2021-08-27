//,temp,MQTTTest.java,1565,1599,temp,MQTTTest.java,1494,1528
//,3
public class xxx {
    @Test(timeout = 30 * 10000)
    public void testSubscribeWithInvalidSingleLevelWildcards() throws Exception {

        MQTT mqtt = createMQTTConnection();
        mqtt.setClientId("MQTT-Client");
        mqtt.setCleanSession(false);

        Topic[] topics = { new Topic("Foo+", QoS.EXACTLY_ONCE),
                           new Topic("+Foo/#", QoS.EXACTLY_ONCE),
                           new Topic("+#", QoS.EXACTLY_ONCE),
                           new Topic("Foo/+X/Level", QoS.EXACTLY_ONCE),
                           new Topic("Foo/+F", QoS.EXACTLY_ONCE) };

        for (int i = 0; i < topics.length; ++i) {
            final BlockingConnection connection = mqtt.blockingConnection();
            connection.connect();

            LOG.info("Trying to subscrobe to topic: {}", topics[i].name());

            try {
                connection.subscribe(new Topic[] { topics[i] });
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
    }

};