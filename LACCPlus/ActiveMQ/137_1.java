//,temp,OptimizedAckTest.java,148,152,temp,FailoverDuplicateTest.java,209,213
//,3
public class xxx {
            @Override
            public boolean isSatisified() throws Exception {
                LOG.info("inflight count: " + regionBroker.getDestinationStatistics().getInflight().getCount());
                return 10 == regionBroker.getDestinationStatistics().getInflight().getCount();
            }

};