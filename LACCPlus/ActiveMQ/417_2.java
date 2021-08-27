//,temp,MQTTTest.java,801,872,temp,MQTTTest.java,648,733
//,3
public class xxx {
    @Test(timeout = 60 * 1000)
    public void testUniqueMessageIds() throws Exception {
        MQTT mqtt = createMQTTConnection();
        mqtt.setClientId("foo");
        mqtt.setKeepAlive((short) 2);
        mqtt.setCleanSession(true);

        final List<PUBLISH> publishList = new ArrayList<PUBLISH>();
        mqtt.setTracer(new Tracer() {
            @Override
            public void onReceive(MQTTFrame frame) {
                LOG.debug("Client received:\n" + frame);
                if (frame.messageType() == PUBLISH.TYPE) {
                    PUBLISH publish = new PUBLISH();
                    try {
                        publish.decode(frame);
                    } catch (ProtocolException e) {
                        fail("Error decoding publish " + e.getMessage());
                    }
                    publishList.add(publish);
                }
            }

            @Override
            public void onSend(MQTTFrame frame) {
                LOG.debug("Client sent:\n" + frame);
            }
        });

        final BlockingConnection connection = mqtt.blockingConnection();
        connection.connect();

        // create overlapping subscriptions with different QoSs
        QoS[] qoss = { QoS.AT_MOST_ONCE, QoS.AT_LEAST_ONCE, QoS.EXACTLY_ONCE };
        final String TOPIC = "TopicA/";

        // publish retained message
        connection.publish(TOPIC, TOPIC.getBytes(), QoS.EXACTLY_ONCE, true);

        String[] subs = { TOPIC, "TopicA/#", "TopicA/+" };
        for (int i = 0; i < qoss.length; i++) {
            connection.subscribe(new Topic[] { new Topic(subs[i], qoss[i]) });
        }

        // publish non-retained message
        connection.publish(TOPIC, TOPIC.getBytes(), QoS.EXACTLY_ONCE, false);
        int received = 0;

        Message msg = connection.receive(2000, TimeUnit.MILLISECONDS);
        do {
            assertNotNull(msg);
            assertEquals(TOPIC, new String(msg.getPayload()));
            msg.ack();
            int waitCount = 0;
            while (publishList.size() <= received && waitCount < 10) {
                Thread.sleep(1000);
                waitCount++;
            }
            msg = connection.receive(2000, TimeUnit.MILLISECONDS);
        } while (msg != null && received++ < subs.length * 2);
        assertEquals("Unexpected number of messages", subs.length * 2, received + 1);

        // make sure we received distinct ids for QoS != AT_MOST_ONCE, and 0 for
        // AT_MOST_ONCE
        for (int i = 0; i < publishList.size(); i++) {
            for (int j = i + 1; j < publishList.size(); j++) {
                final PUBLISH publish1 = publishList.get(i);
                final PUBLISH publish2 = publishList.get(j);
                boolean qos0 = false;
                if (publish1.qos() == QoS.AT_MOST_ONCE) {
                    qos0 = true;
                    assertEquals(0, publish1.messageId());
                }
                if (publish2.qos() == QoS.AT_MOST_ONCE) {
                    qos0 = true;
                    assertEquals(0, publish2.messageId());
                }
                if (!qos0) {
                    assertNotEquals(publish1.messageId(), publish2.messageId());
                }
            }
        }

        connection.unsubscribe(subs);
        connection.disconnect();
    }

};