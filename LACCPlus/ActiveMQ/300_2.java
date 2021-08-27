//,temp,AdvisoryViaNetworkTest.java,301,325,temp,AdvisoryViaNetworkTest.java,274,299
//,3
public class xxx {
    public void testAdvisoryForwardingDuplexNC() throws Exception {
        ActiveMQTopic advisoryTopic = new ActiveMQTopic("ActiveMQ.Advisory.Producer.Topic.FOO");

        createBroker("A");
        createBroker("B");
        NetworkConnector networkBridge = bridgeBrokers("A", "B");
        networkBridge.addStaticallyIncludedDestination(advisoryTopic);
        networkBridge.setDuplex(true);
        startAllBrokers();
        verifyPeerBrokerInfo(brokers.get("A"), 1);


        MessageConsumer consumerA = createConsumer("A", advisoryTopic);
        MessageConsumer consumerB = createConsumer("B", advisoryTopic);

        this.sendMessages("A", new ActiveMQTopic("FOO"), 1);

        MessageIdList messagesA = getConsumerMessages("A", consumerA);
        MessageIdList messagesB = getConsumerMessages("B", consumerB);

        LOG.info("consumerA = " + messagesA);
        LOG.info("consumerB = " + messagesB);

        messagesA.assertMessagesReceived(2);
        messagesB.assertMessagesReceived(2);
    }

};