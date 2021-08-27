//,temp,MemoryMessageStoreQueueCursorTest.java,67,131,temp,StoreQueueCursorNoDuplicateTest.java,70,118
//,3
public class xxx {
    public void testNoDuplicateAfterCacheFullAndReadPast() throws Exception {
        final PersistenceAdapter persistenceAdapter = brokerService
                .getPersistenceAdapter();
        final MessageStore queueMessageStore = persistenceAdapter
                .createQueueMessageStore(destination);
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
        systemUsage.getMemoryUsage().setLimit(messageBytesSize * (count + 2));
        underTest.setSystemUsage(systemUsage);
        underTest.setEnableAudit(false);
        underTest.start();
        assertTrue("cache enabled", underTest.isUseCache() && underTest.isCacheEnabled());

        final ConnectionContext contextNotInTx = new ConnectionContext();
        for (int i = 0; i < count; i++) {
            ActiveMQTextMessage msg = getMessage(i);
            msg.setMemoryUsage(systemUsage.getMemoryUsage());

            queueMessageStore.addMessage(contextNotInTx, msg);
            underTest.addMessageLast(msg);
        }

        assertTrue("cache is disabled as limit reached", !underTest.isCacheEnabled());
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