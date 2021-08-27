//,temp,JMSDurableSubNoLocalChangedTest.java,88,165,temp,DurableSubscriptionUpdatesTest.java,93,168
//,3
public class xxx {
    @Test(timeout = 60000)
    public void testSelectorChange() throws Exception {
        connection = createConnection();
        TopicSession session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

        Topic topic = session.createTopic(topicName);

        // Create a Durable Topic Subscription with noLocal set to true.
        TopicSubscriber durableSubscriber = session.createDurableSubscriber(topic, subscriptionName, "JMSPriority > 8", false);

        // Public first set, only the non durable sub should get these.
        publishToTopic(session, topic, 9);
        publishToTopic(session, topic, 8);

        // Standard subscriber should receive them
        for (int i = 0; i < MSG_COUNT; ++i) {
            Message message = durableSubscriber.receive(2000);
            assertNotNull(message);
            assertEquals(9, message.getJMSPriority());
        }

        // Subscriber should not receive the others.
        {
            Message message = durableSubscriber.receive(500);
            assertNull(message);
        }

        // Public second set for testing durable sub changed.
        publishToTopic(session, topic, 9);

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

        LOG.debug("Testing that updated selector subscription does get any messages.");

        // Recreate a Durable Topic Subscription with noLocal set to false.
        durableSubscriber = session.createDurableSubscriber(topic, subscriptionName, "JMSPriority > 7", false);

        assertEquals(1, brokerService.getAdminView().getDurableTopicSubscribers().length);
        assertEquals(0, brokerService.getAdminView().getInactiveDurableTopicSubscribers().length);

        // Durable subscription should not receive them as the subscriptions should
        // have been removed and recreated to update the noLocal flag.
        {
            Message message = durableSubscriber.receive(500);
            assertNull(message);
        }

        // Public third set which should get queued for the durable sub with noLocal=false
        publishToTopic(session, topic, 8);

        // Durable subscriber should receive them
        for (int i = 0; i < MSG_COUNT; ++i) {
            Message message = durableSubscriber.receive(5000);
            assertNotNull("Should get messages now", message);
            assertEquals(8, message.getJMSPriority());
        }
    }

};