//,temp,DurableSubscriberWithNetworkRestartTest.java,60,117,temp,DurableSubscriberWithNetworkDisconnectTest.java,67,152
//,3
public class xxx {
    public void testSendOnAReceiveOnBWithTransportDisconnect() throws Exception {
        bridgeBrokers(SPOKE, HUB);

        startAllBrokers();

        // Setup connection
        URI hubURI = brokers.get(HUB).broker.getVmConnectorURI();
        URI spokeURI = brokers.get(SPOKE).broker.getVmConnectorURI();
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
        MessageConsumer remoteConsumer = sesSpoke.createDurableSubscriber(topic, consumerName);
        remoteConsumer.setMessageListener(new MessageListener() {
            public void onMessage(Message msg) {
                try {
                    TextMessage textMsg = (TextMessage) msg;
                    receivedMsgs++;
                    LOG.info("Received messages (" + receivedMsgs + "): " + textMsg.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });

        // allow subscription information to flow back to Spoke
        sleep(1000);

        // Setup producer
        MessageProducer localProducer = sesHub.createProducer(topic);
        localProducer.setDeliveryMode(DeliveryMode.PERSISTENT);

        // Send messages
        for (int i = 0; i < MESSAGE_COUNT; i++) {
            sleep(50);
            if (i == 50 || i == 150) {
                if (simulateStalledNetwork) {
                    socketProxy.pause();
                } else {
                    socketProxy.close();
                }
                networkDownTimeStart = System.currentTimeMillis();
            } else if (networkDownTimeStart > 0) {
                // restart after NETWORK_DOWN_TIME seconds
                sleep(NETWORK_DOWN_TIME);
                networkDownTimeStart = 0;
                if (simulateStalledNetwork) {
                    socketProxy.goOn();
                } else {
                    socketProxy.reopen();
                }
            } else {
                // slow message production to allow bridge to recover and limit message duplication
                sleep(500);
            }
            Message test = sesHub.createTextMessage("test-" + i);
            localProducer.send(test);
        }

        LOG.info("waiting for messages to flow");
        Wait.waitFor(new Wait.Condition() {
            @Override
            public boolean isSatisified() throws Exception {
                return receivedMsgs >= MESSAGE_COUNT;
            }
        });

        assertTrue("At least message " + MESSAGE_COUNT +
                " must be received, count=" + receivedMsgs,
                MESSAGE_COUNT <= receivedMsgs);
        brokers.get(HUB).broker.deleteAllMessages();
        brokers.get(SPOKE).broker.deleteAllMessages();
        conHub.close();
        conSpoke.close();
    }

};