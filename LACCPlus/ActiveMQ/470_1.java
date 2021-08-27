//,temp,StoreQueueCursorOrderTest.java,287,386,temp,StoreQueueCursorOrderTest.java,210,285
//,3
public class xxx {
    @Test
    public void testSetBatchWithFuture() throws Exception {
        final int count = 4;
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
        systemUsage.getMemoryUsage().setLimit(messageBytesSize * (count + 6));
        underTest.setSystemUsage(systemUsage);
        underTest.setEnableAudit(false);
        underTest.start();
        assertTrue("cache enabled", underTest.isUseCache() && underTest.isCacheEnabled());

        ActiveMQTextMessage msg = getMessage(0);
        messages[0] = msg;
        msg.setMemoryUsage(systemUsage.getMemoryUsage());
        msg.setRecievedByDFBridge(true);
        final ActiveMQTextMessage msgRef = msg;
        FutureTask<Long> future0 = new FutureTask<Long>(new Runnable() {
            @Override
            public void run() {
                msgRef.getMessageId().setFutureOrSequenceLong(0l);
            }
        }, 0l) {};
        msg.getMessageId().setFutureOrSequenceLong(future0);
        underTest.addMessageLast(msg);
        Executors.newSingleThreadExecutor().submit(future0);


        msg = getMessage(1);
        messages[3] = msg;
        msg.setMemoryUsage(systemUsage.getMemoryUsage());
        msg.setRecievedByDFBridge(true);
        final ActiveMQTextMessage msgRef1 = msg;
        FutureTask<Long> future1 = new FutureTask<Long>(new Runnable() {
            @Override
            public void run() {
                msgRef1.getMessageId().setFutureOrSequenceLong(3l);
            }
        }, 3l) {};
        msg.getMessageId().setFutureOrSequenceLong(future1);
        underTest.addMessageLast(msg);


        msg = getMessage(2);
        messages[1] = msg;
        msg.setMemoryUsage(systemUsage.getMemoryUsage());
        msg.getMessageId().setFutureOrSequenceLong(1l);
        underTest.addMessageLast(msg);

        assertTrue("cache enabled", underTest.isUseCache() && underTest.isCacheEnabled());

        // out of order future
        Executors.newSingleThreadExecutor().submit(future1);

        // sync add to flip cache
        msg = getMessage(3);
        messages[2] = msg;
        msg.setMemoryUsage(systemUsage.getMemoryUsage());
        msg.getMessageId().setFutureOrSequenceLong(2l);
        underTest.addMessageLast(msg);


        assertTrue("cache is disabled as limit reached", !underTest.isCacheEnabled());
        assertEquals("setBatch set", 2l, queueMessageStore.batch.get());

        int dequeueCount = 0;

        underTest.setMaxBatchSize(count);
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

        msg = getMessage(4);
        msg.setMemoryUsage(systemUsage.getMemoryUsage());
        msg.getMessageId().setFutureOrSequenceLong(4l);
        underTest.addMessageLast(msg);

        assertTrue("cache enabled on empty store",  underTest.isCacheEnabled());

    }

};