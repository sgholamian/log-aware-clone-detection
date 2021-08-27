//,temp,AdvisoryViaNetworkTest.java,301,325,temp,AdvisoryViaNetworkTest.java,274,299
//,3
public class xxx {
    public void testBridgeRelevantAdvisoryNotAvailable() throws Exception {
        ActiveMQTopic advisoryTopic = new ActiveMQTopic("ActiveMQ.Advisory.Consumer.Topic.FOO");
        createBroker("A");
        createBroker("B");
        NetworkConnector networkBridge = bridgeBrokers("A", "B");
        networkBridge.addStaticallyIncludedDestination(advisoryTopic);

        startAllBrokers();
        verifyPeerBrokerInfo(brokers.get("A"), 1);


        MessageConsumer consumerA = createConsumer("A", advisoryTopic);
        MessageConsumer consumerB = createConsumer("B", advisoryTopic);

        createConsumer("A", new ActiveMQTopic("FOO"));

        MessageIdList messagesA = getConsumerMessages("A", consumerA);
        MessageIdList messagesB = getConsumerMessages("B", consumerB);

        LOG.info("consumerA = " + messagesA);
        LOG.info("consumerB = " + messagesB);

        messagesA.assertMessagesReceived(1);
        messagesB.assertMessagesReceived(0);
    }

};