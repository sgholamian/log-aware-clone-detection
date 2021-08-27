//,temp,ThreeBrokerTempDestDemandSubscriptionCleanupTest.java,199,203,temp,NonBlockingConsumerRedeliveryTest.java,392,396
//,3
public class xxx {
            @Override
            public boolean isSatisified() throws Exception {
                LOG.info("Lingering temps: " + region.getSubscriptions().size());
                return 0 == region.getSubscriptions().size();
            }

};