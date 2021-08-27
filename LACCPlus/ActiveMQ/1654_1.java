//,temp,ThreeBrokerQueueNetworkTest.java,432,474,temp,ThreeBrokerQueueNetworkTest.java,375,428
//,3
public class xxx {
    public void testMigrateConsumer() throws Exception {
        boolean suppressQueueDuplicateSubscriptions = true;
        boolean decreaseNetworkConsumerPriority = true;
        bridgeAllBrokers("default", 3, suppressQueueDuplicateSubscriptions, decreaseNetworkConsumerPriority);
        startAllBrokers();
        waitForBridgeFormation();
        
        // Setup destination
        Destination dest = createDestination("TEST.FOO", false);    
        
        // Setup consumers
        LOG.info("Consumer on A");
        MessageConsumer clientA = createConsumer("BrokerA", dest);
        
        // ensure advisors have percolated
        Thread.sleep(2000);
        
        LOG.info("Consumer on B");
        int messageCount = 2000;
        CountDownLatch messagesReceived = new CountDownLatch(messageCount);
        MessageConsumer clientB = createConsumer("BrokerB", dest, messagesReceived);
       
        // make the consumer slow so that any network consumer has a chance, even
        // if it has a lower priority
        MessageIdList msgs = getConsumerMessages("BrokerB", clientB);
        msgs.setProcessingDelay(10);
        
        // ensure advisors have percolated
        Thread.sleep(2000);

        LOG.info("Close consumer on A");
        clientA.close();

        // ensure advisors have percolated
        Thread.sleep(2000);
       
        LOG.info("Send to B"); 
        sendMessages("BrokerB", dest, messageCount);

        // Let's try to wait for any messages.
        assertTrue("messages are received within limit", messagesReceived.await(60, TimeUnit.SECONDS));
        assertEquals(messageCount, msgs.getMessageCount());      
    }

};