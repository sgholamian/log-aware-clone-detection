//,temp,MQTTTest.java,577,646,temp,MQTTAuthTest.java,134,197
//,3
public class xxx {
    public void doTestRetainedMessages(String topicName) throws Exception {
        MQTT mqtt = createMQTTConnection();
        mqtt.setKeepAlive((short) 60);

        final String RETAIN = "RETAIN";
        final String TOPICA = topicName;

        final String[] clientIds = { null, "foo", "durable" };
        for (String clientId : clientIds) {
            boolean cleanSession = !"durable".equals(clientId);
            LOG.info("Testing now with Client ID: {} clean: {}", clientId, cleanSession);

            mqtt.setClientId(clientId);
            mqtt.setCleanSession(cleanSession);

            BlockingConnection connection = mqtt.blockingConnection();
            connection.connect();

            // set retained message and check
            connection.publish(TOPICA, RETAIN.getBytes(), QoS.EXACTLY_ONCE, true);
            connection.subscribe(new Topic[]{new Topic(TOPICA, QoS.AT_LEAST_ONCE)});
            Message msg = connection.receive(5000, TimeUnit.MILLISECONDS);
            assertNotNull("No retained message for " + clientId, msg);
            assertEquals(RETAIN, new String(msg.getPayload()));
            msg.ack();
            assertNull(connection.receive(500, TimeUnit.MILLISECONDS));

            // test duplicate subscription
            connection.subscribe(new Topic[]{new Topic(TOPICA, QoS.AT_LEAST_ONCE)});
            msg = connection.receive(15000, TimeUnit.MILLISECONDS);
            assertNotNull("No retained message on duplicate subscription for " + clientId, msg);
            assertEquals(RETAIN, new String(msg.getPayload()));
            msg.ack();
            assertNull(connection.receive(500, TimeUnit.MILLISECONDS));
            connection.unsubscribe(new String[]{TOPICA});

            // clear retained message and check that we don't receive it
            connection.publish(TOPICA, "".getBytes(), QoS.AT_MOST_ONCE, true);
            connection.subscribe(new Topic[]{new Topic(TOPICA, QoS.AT_LEAST_ONCE)});
            msg = connection.receive(500, TimeUnit.MILLISECONDS);
            assertNull("Retained message not cleared for " + clientId, msg);
            connection.unsubscribe(new String[]{TOPICA});

            // set retained message again and check
            connection.publish(TOPICA, RETAIN.getBytes(), QoS.EXACTLY_ONCE, true);
            LOG.info("Performing first subscription");
            connection.subscribe(new Topic[]{new Topic(TOPICA, QoS.AT_LEAST_ONCE)});
            msg = connection.receive(5000, TimeUnit.MILLISECONDS);
            assertNotNull("No reset retained message for " + clientId, msg);
            assertEquals(RETAIN, new String(msg.getPayload()));
            msg.ack();
            assertNull(connection.receive(500, TimeUnit.MILLISECONDS));

            // re-connect and check
            connection.disconnect();
            connection = mqtt.blockingConnection();
            connection.connect();
            LOG.info("Performing second subscription");
            connection.subscribe(new Topic[]{new Topic(TOPICA, QoS.AT_LEAST_ONCE)});
            msg = connection.receive(5000, TimeUnit.MILLISECONDS);
            assertNotNull("No reset retained message for " + clientId, msg);
            assertEquals(RETAIN, new String(msg.getPayload()));
            msg.ack();
            assertNull(connection.receive(500, TimeUnit.MILLISECONDS));

            LOG.info("Test now unsubscribing from: {} for the last time", TOPICA);
            connection.unsubscribe(new String[]{TOPICA});
            connection.disconnect();
        }
    }

};