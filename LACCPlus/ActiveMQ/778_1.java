//,temp,AdvisoryViaNetworkTest.java,359,366,temp,AdvisoryViaNetworkTest.java,262,269
//,3
public class xxx {
            @Override
            public boolean isSatisified() throws Exception {
                RegionBroker regionBroker = (RegionBroker) brokers.get("A").broker.getRegionBroker();
                LOG.info("A Deq:" + regionBroker.getDestinationStatistics().getDequeues().getCount());
                LOG.info("A Inflight:" + regionBroker.getDestinationStatistics().getInflight().getCount());
                return regionBroker.getDestinationStatistics().getDequeues().getCount() > 2
                        && regionBroker.getDestinationStatistics().getInflight().getCount() == 0;
            }

};