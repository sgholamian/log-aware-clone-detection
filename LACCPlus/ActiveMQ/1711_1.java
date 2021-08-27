//,temp,BrowseOverNetworkTest.java,43,75,temp,TwoBrokerNetworkLoadBalanceTest.java,32,67
//,3
public class xxx {
    public void testBrowse() throws Exception {
        createBroker(new URI("broker:(tcp://localhost:61617)/BrokerB?persistent=false&useJmx=false"));
        createBroker(new URI("broker:(tcp://localhost:61616)/BrokerA?persistent=false&useJmx=false"));

        bridgeBrokers("BrokerA", "BrokerB");


        startAllBrokers();

        Destination dest = createDestination("TEST.FOO", false);

        sendMessages("BrokerA", dest, MESSAGE_COUNT);

        Thread.sleep(1000);

        int browsed = browseMessages("BrokerB", dest);

        Thread.sleep(1000);

        MessageConsumer clientA = createConsumer("BrokerA", dest);
        MessageIdList msgsA = getConsumerMessages("BrokerA", clientA);
        msgsA.waitForMessagesToArrive(MESSAGE_COUNT);

        Thread.sleep(1000);
        MessageConsumer clientB = createConsumer("BrokerB", dest);
        MessageIdList msgsB = getConsumerMessages("BrokerB", clientB);
        msgsB.waitForMessagesToArrive(MESSAGE_COUNT);

        LOG.info("A+B: " + msgsA.getMessageCount() + "+"
                + msgsB.getMessageCount());
        assertEquals("Consumer on Broker A, should've consumed all messages", MESSAGE_COUNT, msgsA.getMessageCount());
        assertEquals("Broker B shouldn't get any messages", 0, browsed);
    }

};