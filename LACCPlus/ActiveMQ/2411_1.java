//,temp,MemoryMessageStoreQueueCursorTest.java,67,131,temp,StoreQueueCursorNoDuplicateTest.java,70,118
//,3
public class xxx {
    @Test(timeout = 10000)
    public void testRecoverNextMessages2() throws Exception {
        final MemoryMessageStore queueMessageStore = new MemoryMessageStore(destination);

        final DestinationStatistics destinationStatistics = new DestinationStatistics();
        final Queue queue = new Queue(brokerService, destination, queueMessageStore, destinationStatistics, null);

        queueMessageStore.start();
        queueMessageStore.registerIndexListener(null);

        QueueStorePrefetch myCursor = new QueueStorePrefetch(queue, brokerService.getBroker());
        SystemUsage systemUsage = new SystemUsage();
        // ensure memory limit is reached
        systemUsage.getMemoryUsage().setLimit(messageBytesSize * 5);
        myCursor.setSystemUsage(systemUsage);
        myCursor.setEnableAudit(false);
        myCursor.start();
        assertTrue("cache enabled", myCursor.isUseCache() && myCursor.isCacheEnabled());


        ActiveMQTextMessage msg0 = getMessage(0);
        msg0.setMemoryUsage(systemUsage.getMemoryUsage());
        queueMessageStore.addMessage(null, msg0);
        myCursor.addMessageLast(msg0);
        msg0.decrementReferenceCount();
        if(myCursor.hasNext()) {
            MessageReference ref = myCursor.next();
            LOG.info("Received message: {} with body: ({})", ref.getMessageId(), ((ActiveMQTextMessage)ref.getMessage()).getText());


            //simulate send ack to store to remove message
            myCursor.remove();
            try {
                queueMessageStore.removeMessage(ref.getMessageId());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // simulate full memory (from other resources) such that caching stops
        systemUsage.getMemoryUsage().increaseUsage(messageBytesSize * 10);

        ActiveMQTextMessage msg1 = getMessage(1);
        msg1.setMemoryUsage(systemUsage.getMemoryUsage());
        queueMessageStore.addMessage(null, msg1);
        myCursor.addMessageLast(msg1);
        msg1.decrementReferenceCount();

        boolean b = true;
        while (b) {
            if(myCursor.hasNext()) {
                MessageReference ref = myCursor.next();
                LOG.info("Received message: {} with body: ({})", ref.getMessageId(), ((ActiveMQTextMessage)ref.getMessage()).getText());

                //simulate send ack to store to remove message
                myCursor.remove();
                try {
                    queueMessageStore.removeMessage(ref.getMessageId());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                b = false;
            }
        }
    }

};