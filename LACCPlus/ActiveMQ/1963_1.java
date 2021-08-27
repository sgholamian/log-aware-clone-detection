//,temp,MQTTMaxFrameSizeTest.java,94,121,temp,MQTTMaxFrameSizeTest.java,65,92
//,2
public class xxx {
    @Test(timeout = 30000)
    public void testFrameSizeNotExceededWorks() throws Exception {

        LOG.debug("Starting test on connector {} for frame size: {}", getProtocolScheme(), maxFrameSize);

        MQTT mqtt = createMQTTConnection();
        mqtt.setClientId(getTestName());
        mqtt.setKeepAlive((short) 10);
        mqtt.setVersion("3.1.1");

        BlockingConnection connection = mqtt.blockingConnection();
        connection.connect();

        final int payloadSize = maxFrameSize / 2;

        byte[] payload = new byte[payloadSize];
        for (int i = 0; i < payloadSize; ++i) {
            payload[i] = 42;
        }

        try {
            connection.publish(getTopicName(), payload, QoS.AT_LEAST_ONCE, false);
        } catch (Exception ex) {
            fail("should not have thrown an exception");
        } finally {
            connection.disconnect();
        }
    }

};