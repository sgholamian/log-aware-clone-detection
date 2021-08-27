//,temp,AMQ4485LowLimitTest.java,236,254,temp,AMQ4485NetworkOfXBrokersWithNDestsFanoutTransactionTest.java,161,173
//,3
public class xxx {
            @Override
            public boolean isSatisified() throws Exception {
                for (ConsumerState tally : consumerStates) {
                    final int expected = numMessages * (tally.destination.isComposite() ? tally.destination.getCompositeDestinations().length : 1);
                    LOG.info("Tally for: " + tally.brokerName + ", dest: " + tally.destination + " - " + tally.accumulator.get());
                    if (tally.accumulator.get() != expected) {
                        LOG.info("Tally for: " + tally.brokerName + ", dest: " + tally.destination + " - " + tally.accumulator.get() + " != " + expected + ", " + tally.expected);
                        return false;
                    }
                    LOG.info("got tally on " + tally.brokerName);
                }
                return true;
            }

};