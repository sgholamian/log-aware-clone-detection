//,temp,ThreeBrokerQueueNetworkTest.java,432,474,temp,ThreeBrokerQueueNetworkTest.java,375,428
//,3
public class xxx {
    public void XtestMigrateConsumerStuckMessages() throws Exception {
        boolean suppressQueueDuplicateSubscriptions = false;
        bridgeAllBrokers("default", 3, suppressQueueDuplicateSubscriptions);
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
        
        // will only get half of the messages
        CountDownLatch messagesReceived = new CountDownLatch(messageCount/2);
        MessageConsumer clientB = createConsumer("BrokerB", dest, messagesReceived);
          
        // ensure advisors have percolated
        Thread.sleep(2000);

        LOG.info("Close consumer on A");
        clientA.close();

        // ensure advisors have percolated
        Thread.sleep(2000);
       
        LOG.info("Send to B"); 
        sendMessages("BrokerB", dest, messageCount);

        // Let's try to wait for any messages.
        assertTrue(messagesReceived.await(30, TimeUnit.SECONDS));

        // Get message count
        MessageIdList msgs = getConsumerMessages("BrokerB", clientB);
        
        // see will any more arrive
        Thread.sleep(500);        
        assertEquals(messageCount/2, msgs.getMessageCount());
        
        // pick up the stuck messages
        messagesReceived = new CountDownLatch(messageCount/2);
        clientA = createConsumer("BrokerA", dest, messagesReceived);
        // Let's try to wait for any messages.
        assertTrue(messagesReceived.await(30, TimeUnit.SECONDS));
        
        msgs = getConsumerMessages("BrokerA", clientA);
        assertEquals(messageCount/2, msgs.getMessageCount());
    }

};