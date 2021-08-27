//,temp,DurableSubInBrokerNetworkTest.java,118,165,temp,DurableSubInBrokerNetworkTest.java,81,116
//,3
public class xxx {
    public void testTwoDurableSubsInNetworkWithUnsubscribe() throws Exception{

        // create 1st durable sub to topic TEST.FOO
        ActiveMQConnectionFactory fact = new ActiveMQConnectionFactory(connector.getConnectUri().toString());
        Connection conn = fact.createConnection();
        conn.setClientID("clientID1");
        Session session = conn.createSession(false, 1);
        Destination dest = session.createTopic(topicName);
        TopicSubscriber sub = session.createDurableSubscriber((Topic)dest, subName);
        LOG.info("Durable subscription of name " + subName + "created.");
        TopicSubscriber sub2 = session.createDurableSubscriber((Topic) dest, subName2);
        LOG.info("Durable subscription of name " + subName2 + "created.");

        Thread.sleep(100);

        // query durable sub on local and remote broker
        // raise an error if not found

        assertTrue(foundSubInLocalBroker(subName));
        assertTrue(foundSubInLocalBroker(subName2));


        assertTrue(foundSubInRemoteBrokerByTopicName(topicName));

        // unsubscribe from durable sub
        sub.close();
        session.unsubscribe(subName);
        LOG.info("Unsubscribed from durable subscription.");
        Thread.sleep(100);

        // query durable sub on local and remote broker
        assertFalse(foundSubInLocalBroker(subName));
        assertTrue(foundSubInLocalBroker(subName2));

        assertTrue("Durable subscription should still be on remote broker",
                foundSubInRemoteBrokerByTopicName(topicName));

        sub2.close();
        session.unsubscribe(subName2);

        Thread.sleep(100);

        assertFalse(foundSubInLocalBroker(subName2));

        assertFalse("Durable subscription not unregistered on remote broker",
                foundSubInRemoteBrokerByTopicName(topicName));

    }

};