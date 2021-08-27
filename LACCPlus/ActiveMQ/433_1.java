//,temp,AMQ4485LowLimitTest.java,208,265,temp,AMQ4485NetworkOfXBrokersWithNDestsFanoutTransactionTest.java,133,181
//,3
public class xxx {
    public void testBrokers() throws Exception {

        buildUrlList();

        for (int i = 0; i < numBrokers; i++) {
            createBroker(i);
        }

        startAllBrokers();
        waitForBridgeFormation(numBrokers - 1);

        verifyPeerBrokerInfos(numBrokers - 1);


        final List<ConsumerState> consumerStates = startAllGWConsumers(numBrokers);

        startAllGWFanoutConsumers(numBrokers);

        LOG.info("Waiting for percolation of consumers..");
        TimeUnit.SECONDS.sleep(5);

        LOG.info("Produce mesages..");
        long startTime = System.currentTimeMillis();

        // produce
        produce(numMessages);

        assertTrue("Got all sent", Wait.waitFor(new Wait.Condition() {
            @Override
            public boolean isSatisified() throws Exception {
                for (ConsumerState tally : consumerStates) {
                    final int expected = numMessages * (tally.destination.isComposite() ? tally.destination.getCompositeDestinations().length : 1);
                    LOG.info("Tally for: " + tally.brokerName + ", dest: " + tally.destination + " - " + tally.accumulator.get());
                    if (tally.accumulator.get() != expected) {
                        LOG.info("Tally for: " + tally.brokerName + ", dest: " + tally.destination + " - " + tally.accumulator.get() + " != " + expected + ", " + tally.expected);
                        if (tally.accumulator.get() > expected - 50) {
                            dumpQueueStat(null);
                        }
                        if (tally.expected.size() == 1) {
                            startConsumer(tally.brokerName, tally.destination);
                        };
                        return false;
                    }
                    LOG.info("got tally on " + tally.brokerName);
                }
                return true;
            }
        }, 1000 * 60 * 1000l, 20*1000));

        assertTrue("No exceptions:" + exceptions, exceptions.isEmpty());

        LOG.info("done");
        long duration = System.currentTimeMillis() - startTime;
        LOG.info("Duration:" + TimeUtils.printDuration(duration));

        assertEquals("nothing in the dlq's", 0, dumpQueueStat(new ActiveMQQueue("ActiveMQ.DLQ")));

    }

};