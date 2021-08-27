//,temp,AMQ4607Test.java,126,179,temp,AMQ4607Test.java,79,124
//,3
public class xxx {
    public void testMigratingConsumer() throws Exception {
        bridge("Broker0", "Broker1");
        if (!duplex) bridge("Broker1", "Broker0");

        bridge("Broker1", "Broker2");
        if (!duplex) bridge("Broker2", "Broker1");

        bridge("Broker0", "Broker2");
        if (!duplex) bridge("Broker2", "Broker0");

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

            assertExactMessageCount("Broker" + i, dest, 1, TIMEOUT);

            messageConsumer.close();
            LOG.info("Check for no consumers..");
            for (int J = 0; J < BROKER_COUNT; J++) {
        	    assertExactConsumersConnect("Broker" + J, dest, 0, TIMEOUT);
            }
        }

        // now consume the message
        final String brokerId = "Broker2";
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