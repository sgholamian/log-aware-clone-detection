//,temp,StoreQueueCursorOrderTest.java,287,386,temp,StoreQueueCursorOrderTest.java,210,285
//,3
public class xxx {
    @Test
    public void testSetBatchWithOrderedFutureCurrentFuture() throws Exception {
        final int count = 2;
        final Message[] messages = new Message[count];
        final TestMessageStore queueMessageStore = new TestMessageStore(messages, destination);
        final ConsumerInfo consumerInfo = new ConsumerInfo();
        final DestinationStatistics destinationStatistics = new DestinationStatistics();
        consumerInfo.setExclusive(true);

        final Queue queue = new Queue(brokerService, destination,
                queueMessageStore, destinationStatistics, null);

        queueMessageStore.start();
        queueMessageStore.registerIndexListener(null);

        QueueStorePrefetch underTest = new QueueStorePrefetch(queue, brokerService.getBroker());
        SystemUsage systemUsage = new SystemUsage();
        // ensure memory limit is reached
        systemUsage.getMemoryUsage().setLimit(messageBytesSize * 1);
        underTest.setSystemUsage(systemUsage);
        underTest.setEnableAudit(false);
        underTest.start();
        assertTrue("cache enabled", underTest.isUseCache() && underTest.isCacheEnabled());

        ActiveMQTextMessage msg = getMessage(0);
        messages[0] = msg;
        msg.setMemoryUsage(systemUsage.getMemoryUsage());
        msg.setRecievedByDFBridge(true);
        final ActiveMQTextMessage msgRef = msg;
        FutureTask<Long> future = new FutureTask<Long>(new Runnable() {
            @Override
            public void run() {
                msgRef.getMessageId().setFutureOrSequenceLong(0l);
            }
        }, 0l) {};
        msg.getMessageId().setFutureOrSequenceLong(future);
        Executors.newSingleThreadExecutor().submit(future);
        underTest.addMessageLast(msg);

        assertTrue("cache enabled", underTest.isUseCache() && underTest.isCacheEnabled());

        // second message will flip the cache but will be stored before the future task
        msg = getMessage(1);
        messages[1] = msg;
        msg.setMemoryUsage(systemUsage.getMemoryUsage());
        msg.setRecievedByDFBridge(true);
        final ActiveMQTextMessage msgRe2f = msg;
        FutureTask<Long> future2 = new FutureTask<Long>(new Runnable() {
            @Override
            public void run() {
                msgRe2f.getMessageId().setFutureOrSequenceLong(1l);
            }
        }, 1l) {};
        msg.getMessageId().setFutureOrSequenceLong(future2);
        Executors.newSingleThreadExecutor().submit(future2);
        underTest.addMessageLast(msg);


        assertTrue("cache is disabled as limit reached", !underTest.isCacheEnabled());
        assertEquals("setBatch set", 1l, queueMessageStore.batch.get());

        int dequeueCount = 0;

        underTest.setMaxBatchSize(2);
        underTest.reset();
        while (underTest.hasNext() && dequeueCount < count) {
            MessageReference ref = underTest.next();
            ref.decrementReferenceCount();
            underTest.remove();
            LOG.info("Received message: {} with body: {}",
                     ref.getMessageId(), ((ActiveMQTextMessage)ref.getMessage()).getText());
            assertEquals(dequeueCount++, ref.getMessageId().getProducerSequenceId());
        }
        underTest.release();
        assertEquals(count, dequeueCount);
    }

};