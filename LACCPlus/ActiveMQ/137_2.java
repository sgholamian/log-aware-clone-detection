//,temp,OptimizedAckTest.java,148,152,temp,FailoverDuplicateTest.java,209,213
//,3
public class xxx {
            @Override
            public boolean isSatisified() throws Exception {
                LOG.info("dequeues : "   + ((RegionBroker) broker.getRegionBroker()).getDestinationStatistics().getDequeues().getCount());
                return  totalSent + 1 <= ((RegionBroker) broker.getRegionBroker()).getDestinationStatistics().getDequeues().getCount();
            }

};