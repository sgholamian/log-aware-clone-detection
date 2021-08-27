//,temp,MQTTAuthTest.java,282,319,temp,MQTTAuthTest.java,90,132
//,3
public class xxx {
    @Test(timeout = 60 * 1000)
    public void testInvalidClientIdGetCorrectErrorCode() throws Exception {
        MQTT mqttPub = createMQTTConnection("invalid", true);

        final AtomicInteger errorCode = new AtomicInteger();

        mqttPub.setTracer(new Tracer() {
            @Override
            public void onReceive(MQTTFrame frame) {
                LOG.info("Client received: {}", frame);
                if (frame.messageType() == CONNACK.TYPE) {
                    CONNACK connAck = new CONNACK();
                    try {
                        connAck.decode(frame);
                        LOG.info("{}", connAck);
                        errorCode.set(connAck.code().ordinal());
                        assertEquals(CONNACK.Code.CONNECTION_REFUSED_IDENTIFIER_REJECTED, connAck.code());
                    } catch (ProtocolException e) {
                        fail("Error decoding publish " + e.getMessage());
                    }
                }
            }

            @Override
            public void onSend(MQTTFrame frame) {
                LOG.info("Client sent: {}", frame);
            }
        });

        BlockingConnection connectionPub = mqttPub.blockingConnection();
        try {
            connectionPub.connect();
            fail("Should not be able to connect.");
        } catch (Exception e) {
        }

        assertEquals(CONNACK.Code.CONNECTION_REFUSED_IDENTIFIER_REJECTED.ordinal(), errorCode.get());
    }

};