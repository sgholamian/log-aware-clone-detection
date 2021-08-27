//,temp,DurableSubscriberWithNetworkRestartTest.java,60,117,temp,DurableSubscriberWithNetworkDisconnectTest.java,67,152
//,3
public class xxx {
    public void testSendOnAReceiveOnBWithTransportDisconnect() throws Exception {
        bridge(SPOKE, HUB);
        startAllBrokers();

        verifyDuplexBridgeMbean();

        // Setup connection
        URI hubURI = brokers.get(HUB).broker.getTransportConnectors().get(0).getPublishableConnectURI();
        URI spokeURI = brokers.get(SPOKE).broker.getTransportConnectors().get(0).getPublishableConnectURI();
        ActiveMQConnectionFactory facHub = new ActiveMQConnectionFactory(hubURI);
        ActiveMQConnectionFactory facSpoke = new ActiveMQConnectionFactory(spokeURI);
        Connection conHub = facHub.createConnection();
        Connection conSpoke = facSpoke.createConnection();
        conHub.setClientID("clientHUB");
        conSpoke.setClientID("clientSPOKE");
        conHub.start();
        conSpoke.start();
        Session sesHub = conHub.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Session sesSpoke = conSpoke.createSession(false, Session.AUTO_ACKNOWLEDGE);

        ActiveMQTopic topic = new ActiveMQTopic("TEST.FOO");
        String consumerName = "consumerName";

        // Setup consumers
        MessageConsumer remoteConsumer = sesHub.createDurableSubscriber(topic, consumerName);
        sleep(1000);
        remoteConsumer.close();

        // Setup producer
        MessageProducer localProducer = sesSpoke.createProducer(topic);
        localProducer.setDeliveryMode(DeliveryMode.PERSISTENT);

        final String payloadString = new String(new byte[10*1024]);
        // Send messages
        for (int i = 0; i < MESSAGE_COUNT; i++) {
            Message test = sesSpoke.createTextMessage("test-" + i);
            test.setStringProperty("payload", payloadString);
            localProducer.send(test);
        }
        localProducer.close();

        final String options = "?persistent=true&useJmx=true&deleteAllMessagesOnStartup=false";
        for (int i=0;i<2;i++) {
            brokers.get(SPOKE).broker.stop();
            sleep(1000);
            createBroker(new URI("broker:(tcp://localhost:61616)/" + SPOKE + options));
            bridge(SPOKE, HUB);
            brokers.get(SPOKE).broker.start();
            LOG.info("restarted spoke..:" + i);

            assertTrue("got mbeans on restart", Wait.waitFor(new Wait.Condition() {
                @Override
                public boolean isSatisified() throws Exception {
                    return countMbeans( brokers.get(HUB).broker, "networkBridge", 20000) == (dynamicOnly ? 1 : 2);
                }
            }));
        }
    }

};