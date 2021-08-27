//,temp,MQTTTest.java,801,872,temp,MQTTTest.java,648,733
//,3
public class xxx {
    @Test(timeout = 90 * 1000)
    public void testPacketIdGeneratorNonCleanSession() throws Exception {
        final MQTT mqtt = createMQTTConnection("nonclean-packetid", false);
        mqtt.setKeepAlive((short) 15);

        final Map<Short, PUBLISH> publishMap = new ConcurrentHashMap<Short, PUBLISH>();
        mqtt.setTracer(new Tracer() {
            @Override
            public void onReceive(MQTTFrame frame) {
                LOG.debug("Client received:\n" + frame);
                if (frame.messageType() == PUBLISH.TYPE) {
                    PUBLISH publish = new PUBLISH();
                    try {
                        publish.decode(frame);
                        LOG.debug("PUBLISH " + publish);
                    } catch (ProtocolException e) {
                        fail("Error decoding publish " + e.getMessage());
                    }
                    if (publishMap.get(publish.messageId()) != null) {
                        assertTrue(publish.dup());
                    }
                    publishMap.put(publish.messageId(), publish);
                }
            }

            @Override
            public void onSend(MQTTFrame frame) {
                LOG.debug("Client sent:\n" + frame);
            }
        });

        BlockingConnection connection = mqtt.blockingConnection();
        connection.connect();
        final String TOPIC = "TopicA/";
        connection.subscribe(new Topic[] { new Topic(TOPIC, QoS.EXACTLY_ONCE) });

        // publish non-retained messages
        final int TOTAL_MESSAGES = 10;
        for (int i = 0; i < TOTAL_MESSAGES; i++) {
            connection.publish(TOPIC, TOPIC.getBytes(), QoS.EXACTLY_ONCE, false);
        }

        // receive half the messages in this session
        for (int i = 0; i < TOTAL_MESSAGES / 2; i++) {
            final Message msg = connection.receive(1000, TimeUnit.MILLISECONDS);
            assertNotNull(msg);
            assertEquals(TOPIC, new String(msg.getPayload()));
            msg.ack();
        }

        connection.disconnect();
        // resume session
        connection = mqtt.blockingConnection();
        connection.connect();
        // receive rest of the messages
        Message msg = null;
        do {
            msg = connection.receive(1000, TimeUnit.MILLISECONDS);
            if (msg != null) {
                assertEquals(TOPIC, new String(msg.getPayload()));
                msg.ack();
            }
        } while (msg != null);

        // make sure we received all message ids
        for (short id = 1; id <= TOTAL_MESSAGES; id++) {
            assertNotNull("No message for id " + id, publishMap.get(id));
        }

        connection.unsubscribe(new String[] { TOPIC });
        connection.disconnect();
    }

};