//,temp,ThreeBrokerTempDestDemandSubscriptionCleanupTest.java,133,137,temp,NonBlockingConsumerRedeliveryTest.java,415,419
//,3
public class xxx {
            @Override
            public boolean isSatisified() throws Exception {
                LOG.info("Lingering temps: " + region.getSubscriptions().size());
                return 0 == region.getSubscriptions().size();
            }

};