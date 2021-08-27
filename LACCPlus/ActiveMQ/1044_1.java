//,temp,AMQ4607Test.java,126,179,temp,AMQ4607Test.java,79,124
//,3
public class xxx {
    public void testMigratingConsumerFullCircle() throws Exception {
        bridge("Broker0", "Broker1");
        if (!duplex) bridge("Broker1", "Broker0");

        bridge("Broker1", "Broker2");
        if (!duplex) bridge("Broker2", "Broker1");

        bridge("Broker0", "Broker2");
        if (!duplex) bridge("Broker2", "Broker0");

        // allow full loop, immediate replay back to 0 from 2
        ConditionalNetworkBridgeFilterFactory conditionalNetworkBridgeFilterFactory = new ConditionalNetworkBridgeFilterFactory();
        conditionalNetworkBridgeFilterFactory.setReplayDelay(0);
        conditionalNetworkBridgeFilterFactory.setReplayWhenNoConsumers(true);
        brokers.get("Broker2").broker.getDestinationPolicy().getDefaultEntry().setNetworkBridgeFilterFactory(conditionalNetworkBridgeFilterFactory);
        startAllBrokers();
        this.waitForBridgeFormation();

        Destination dest = createDestination("TEST.FOO", false);

        sendMessages("Broker0", dest, 1);

        for (int i=0; i< BROKER_COUNT; i++) {
            MessageConsumer messageConsumer = createConsumer("Broker" + i, dest, "DoNotConsume = 'true'");

            for (int J = 0; J < BROKER_COUNT; J++) {
                assertExactConsumersConnect("Broker" + J, dest, CONSUMER_COUNT, TIMEOUT);
            }

            assertNoUnhandeledExceptions();

            // validate the message has been forwarded
            assertExactMessageCount("Broker" + i, dest, 1, TIMEOUT);

            messageConsumer.close();
            LOG.info("Check for no consumers..");
            for (int J = 0; J < BROKER_COUNT; J++) {
        	    assertExactConsumersConnect("Broker" + J, dest, 0, TIMEOUT);
            }
        }

        // now consume the message from the origin
        LOG.info("Consume from origin...");
        final String brokerId = "Broker0";
        MessageConsumer messageConsumer = createConsumer(brokerId, dest);
        assertTrue("Consumed ok", Wait.waitFor(new Wait.Condition() {
            @Override
            public boolean isSatisified() throws Exception {
                return brokers.get(brokerId).allMessages.getMessageIds().size() == 1;
            }
        }));
        messageConsumer.close();

    }

};