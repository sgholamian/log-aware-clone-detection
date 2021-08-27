//,temp,SubscriptionRecoveryTest.java,189,278,temp,SubscriptionRecoveryTest.java,117,187
//,3
public class xxx {
    @Test
    public void testDurableSubPrefetchRecovered() throws Exception {

        ActiveMQQueue queue = new ActiveMQQueue("MyQueue");
        ActiveMQTopic topic = new ActiveMQTopic("MyDurableTopic");

        // Send to a Queue to create some journal files
        sendMessages(queue);

        LOG.info("There are currently [{}] journal log files.", getNumberOfJournalFiles());

        createInactiveDurableSub(topic);

        assertTrue("Should have an inactive durable sub", Wait.waitFor(new Wait.Condition() {
            @Override
            public boolean isSatisified() throws Exception {
                ObjectName[] subs = service.getAdminView().getInactiveDurableTopicSubscribers();
                return subs != null && subs.length == 1 ? true : false;
            }
        }));

        // Now send some more to the queue to create even more files.
        sendMessages(queue);

        LOG.info("There are currently [{}] journal log files.", getNumberOfJournalFiles());
        assertTrue(getNumberOfJournalFiles() > 1);

        LOG.info("Restarting the broker.");
        restartBroker();
        LOG.info("Restarted the broker.");

        LOG.info("There are currently [{}] journal log files.", getNumberOfJournalFiles());
        assertTrue(getNumberOfJournalFiles() > 1);

        assertTrue("Should have an inactive durable sub", Wait.waitFor(new Wait.Condition() {
            @Override
            public boolean isSatisified() throws Exception {
                ObjectName[] subs = service.getAdminView().getInactiveDurableTopicSubscribers();
                return subs != null && subs.length == 1 ? true : false;
            }
        }));

        // Clear out all queue data
        service.getAdminView().removeQueue(queue.getQueueName());

        assertTrue("Less than two journal files expected, was " + getNumberOfJournalFiles(), Wait.waitFor(new Wait.Condition() {
            @Override
            public boolean isSatisified() throws Exception {
                return getNumberOfJournalFiles() <= 2;
            }
        }, TimeUnit.MINUTES.toMillis(2)));

        LOG.info("Sending {} Messages to the Topic.", MSG_COUNT);
        // Send some messages to the inactive destination
        sendMessages(topic);

        LOG.info("Attempt to consume {} messages from the Topic.", MSG_COUNT);
        assertEquals(MSG_COUNT, consumeFromInactiveDurableSub(topic));

        LOG.info("Recovering the broker.");
        recoverBroker();
        LOG.info("Recovering the broker.");

        assertTrue("Should have an inactive durable sub", Wait.waitFor(new Wait.Condition() {
            @Override
            public boolean isSatisified() throws Exception {
                ObjectName[] subs = service.getAdminView().getInactiveDurableTopicSubscribers();
                return subs != null && subs.length == 1 ? true : false;
            }
        }));
    }

};