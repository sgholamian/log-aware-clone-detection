//,temp,BrowseOverNetworkTest.java,43,75,temp,TwoBrokerNetworkLoadBalanceTest.java,32,67
//,3
public class xxx {
    public void testLoadBalancing() throws Exception {
        bridgeBrokers("BrokerA", "BrokerB");
        bridgeBrokers("BrokerB", "BrokerA");

        startAllBrokers();
        waitForBridgeFormation();

        // Setup destination
        Destination dest = createDestination("TEST.FOO", false);

        // Setup consumers
        MessageConsumer clientA = createConsumer("BrokerA", dest);

     // Setup consumers
        MessageConsumer clientB = createConsumer("BrokerB", dest);
        
        // Send messages
        sendMessages("BrokerA", dest, 5000);

        // Send messages
        sendMessages("BrokerB", dest, 1000);

        // Get message count
        final MessageIdList msgsA = getConsumerMessages("BrokerA", clientA);
        final MessageIdList msgsB = getConsumerMessages("BrokerB", clientB);

        Wait.waitFor(new Wait.Condition() {
            public boolean isSatisified() throws Exception {
                return msgsA.getMessageCount() + msgsB.getMessageCount() == 6000;
            }});
        
        LOG.info("A got: " +  msgsA.getMessageCount());
        LOG.info("B got: " +  msgsB.getMessageCount());
         
        assertTrue("B got is fair share: " + msgsB.getMessageCount(), msgsB.getMessageCount() > 2000);
    }

};