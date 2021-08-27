//,temp,JMSDurableSubNoLocalChangedTest.java,167,256,temp,DurableSubscriptionUpdatesTest.java,170,247
//,3
public class xxx {
    @Test(timeout = 60000)
    public void testResubscribeWithNewNoLocalValueNoBrokerRestart() throws Exception {
        connection = createConnection();
        TopicSession session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

        Topic topic = session.createTopic(topicName);

        // Create a Durable Topic Subscription with noLocal set to true.
        TopicSubscriber durableSubscriber = session.createDurableSubscriber(topic, subscriptionName, null, true);

        // Create a Durable Topic Subscription with noLocal set to true.
        TopicSubscriber nonDurableSubscriber = session.createSubscriber(topic);

        // Public first set, only the non durable sub should get these.
        publishToTopic(session, topic);

        LOG.debug("Testing that noLocal=true subscription doesn't get any messages.");

        // Standard subscriber should receive them
        for (int i = 0; i < MSG_COUNT; ++i) {
            Message message = nonDurableSubscriber.receive(2000);
            assertNotNull(message);
        }

        // Durable noLocal=true subscription should not receive them
        {
            Message message = durableSubscriber.receive(500);
            assertNull(message);
        }

        // Public second set for testing durable sub changed.
        publishToTopic(session, topic);

        assertEquals(1, brokerService.getAdminView().getDurableTopicSubscribers().length);
        assertEquals(0, brokerService.getAdminView().getInactiveDurableTopicSubscribers().length);

        // Durable now goes inactive.
        durableSubscriber.close();

        assertTrue("Should have no durables.", Wait.waitFor(new Wait.Condition() {

            @Override
            public boolean isSatisified() throws Exception {
                return brokerService.getAdminView().getDurableTopicSubscribers().length == 0;
            }
        }));
        assertTrue("Should have an inactive sub.", Wait.waitFor(new Wait.Condition() {

            @Override
            public boolean isSatisified() throws Exception {
                return brokerService.getAdminView().getInactiveDurableTopicSubscribers().length == 1;
            }
        }));

        LOG.debug("Testing that updated noLocal=false subscription does get any messages.");

        // Recreate a Durable Topic Subscription with noLocal set to false.
        durableSubscriber = session.createDurableSubscriber(topic, subscriptionName, null, false);

        assertEquals(1, brokerService.getAdminView().getDurableTopicSubscribers().length);
        assertEquals(0, brokerService.getAdminView().getInactiveDurableTopicSubscribers().length);

        // Durable noLocal=false subscription should not receive them as the subscriptions should
        // have been removed and recreated to update the noLocal flag.
        {
            Message message = durableSubscriber.receive(500);
            assertNull(message);
        }

        // Public third set which should get queued for the durable sub with noLocal=false
        publishToTopic(session, topic);

        // Durable subscriber should receive them
        for (int i = 0; i < MSG_COUNT; ++i) {
            Message message = durableSubscriber.receive(5000);
            assertNotNull("Should get local messages now", message);
        }
    }

};