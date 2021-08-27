//,temp,DurableSubInBrokerNetworkTest.java,118,165,temp,DurableSubInBrokerNetworkTest.java,81,116
//,3
public class xxx {
    public void testDurableSubNetwork() throws Exception {
        LOG.info("testDurableSubNetwork started.");

        // create durable sub
        ActiveMQConnectionFactory fact = new ActiveMQConnectionFactory(connector.getConnectUri().toString());
        Connection conn = fact.createConnection();
        conn.setClientID("clientID1");
        Session session = conn.createSession(false, 1);
        Destination dest = session.createTopic(topicName);
        TopicSubscriber sub = session.createDurableSubscriber((Topic)dest, subName);
        LOG.info("Durable subscription of name " + subName + "created.");
        Thread.sleep(100);

        // query durable sub on local and remote broker
        // raise an error if not found

        assertTrue(foundSubInLocalBroker(subName));


        assertTrue(foundSubInRemoteBrokerByTopicName(topicName));

        // unsubscribe from durable sub
        sub.close();
        session.unsubscribe(subName);
        LOG.info("Unsubscribed from durable subscription.");
        Thread.sleep(100);

        // query durable sub on local and remote broker
        // raise an error if its not removed from both brokers
        assertFalse(foundSubInLocalBroker(subName));

        assertFalse("Durable subscription not unregistered on remote broker",
                foundSubInRemoteBrokerByTopicName(topicName));


    }

};