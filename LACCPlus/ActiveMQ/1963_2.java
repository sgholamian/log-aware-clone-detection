//,temp,MQTTMaxFrameSizeTest.java,94,121,temp,MQTTMaxFrameSizeTest.java,65,92
//,2
public class xxx {
    @Test(timeout = 30000)
    public void testFrameSizeToLargeClosesConnection() throws Exception {

        LOG.debug("Starting test on connector {} for frame size: {}", getProtocolScheme(), maxFrameSize);

        MQTT mqtt = createMQTTConnection();
        mqtt.setClientId(getTestName());
        mqtt.setKeepAlive((short) 10);
        mqtt.setVersion("3.1.1");

        BlockingConnection connection = mqtt.blockingConnection();
        connection.connect();

        final int payloadSize = maxFrameSize + 100;

        byte[] payload = new byte[payloadSize];
        for (int i = 0; i < payloadSize; ++i) {
            payload[i] = 42;
        }

        try {
            connection.publish(getTopicName(), payload, QoS.AT_LEAST_ONCE, false);
            fail("should have thrown an exception");
        } catch (Exception ex) {
        } finally {
            connection.disconnect();
        }
    }

};