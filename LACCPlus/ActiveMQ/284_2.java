//,temp,MQTTAuthTest.java,282,319,temp,MQTTAuthTest.java,90,132
//,3
public class xxx {
    @Test(timeout = 60 * 1000)
    public void testBadUserNameOrPasswordGetsConnAckWithErrorCode() throws Exception {
        MQTT mqttPub = createMQTTConnection("pub", true);
        mqttPub.setUserName("foo");
        mqttPub.setPassword("bar");

        final AtomicBoolean failed = new AtomicBoolean();

        mqttPub.setTracer(new Tracer() {
            @Override
            public void onReceive(MQTTFrame frame) {
                LOG.info("Client received: {}", frame);
                if (frame.messageType() == CONNACK.TYPE) {
                    CONNACK connAck = new CONNACK();
                    try {
                        connAck.decode(frame);
                        LOG.info("{}", connAck);
                        assertEquals(CONNACK.Code.CONNECTION_REFUSED_NOT_AUTHORIZED, connAck.code());
                    } catch (ProtocolException e) {
                        failed.set(true);
                        fail("Error decoding publish " + e.getMessage());
                    } catch (Throwable err) {
                        failed.set(true);
                        throw err;
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

        assertFalse("connection should have failed.", failed.get());
    }

};