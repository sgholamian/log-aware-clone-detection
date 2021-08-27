//,temp,SubscriptionRecoveryTest.java,189,278,temp,SubscriptionRecoveryTest.java,117,187
//,3
public class xxx {
    @Test
    public void testDurableAcksNotDropped() throws Exception {

        ActiveMQQueue queue = new ActiveMQQueue("MyQueue");
        ActiveMQTopic topic = new ActiveMQTopic("MyDurableTopic");

        // Create durable sub in first data file.
        createInactiveDurableSub(topic);

        assertTrue("Should have an inactive durable sub", Wait.waitFor(new Wait.Condition() {
            @Override
            public boolean isSatisified() throws Exception {
                ObjectName[] subs = service.getAdminView().getInactiveDurableTopicSubscribers();
                return subs != null && subs.length == 1 ? true : false;
            }
        }));

        // Send to a Topic
        sendMessages(topic, 1);

        // Send to a Queue to create some journal files
        sendMessages(queue);

        LOG.info("Before consume there are currently [{}] journal log files.", getNumberOfJournalFiles());

        // Consume all the Messages leaving acks behind.
        consumeDurableMessages(topic, 1);

        LOG.info("After consume there are currently [{}] journal log files.", getNumberOfJournalFiles());

        // Now send some more to the queue to create even more files.
        sendMessages(queue);

        LOG.info("More Queued. There are currently [{}] journal log files.", getNumberOfJournalFiles());
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

        assertTrue("Less than three journal file expected, was " + getNumberOfJournalFiles(), Wait.waitFor(new Wait.Condition() {
            @Override
            public boolean isSatisified() throws Exception {
                return getNumberOfJournalFiles() <= 3;
            }
        }, TimeUnit.MINUTES.toMillis(3)));

        // See if we receive any message they should all be acked.
        tryConsumeExpectNone(topic);

        LOG.info("There are currently [{}] journal log files.", getNumberOfJournalFiles());

        LOG.info("Recovering the broker.");
        recoverBroker();
        LOG.info("Recovering the broker.");

        LOG.info("There are currently [{}] journal log files.", getNumberOfJournalFiles());

        assertTrue("Should have an inactive durable sub", Wait.waitFor(new Wait.Condition() {
            @Override
            public boolean isSatisified() throws Exception {
                ObjectName[] subs = service.getAdminView().getInactiveDurableTopicSubscribers();
                return subs != null && subs.length == 1 ? true : false;
            }
        }));

        // See if we receive any message they should all be acked.
        tryConsumeExpectNone(topic);

        assertTrue("Less than three journal file expected, was " + getNumberOfJournalFiles(), Wait.waitFor(new Wait.Condition() {
            @Override
            public boolean isSatisified() throws Exception {
                return getNumberOfJournalFiles() == 1;
            }
        }, TimeUnit.MINUTES.toMillis(1)));
    }

};