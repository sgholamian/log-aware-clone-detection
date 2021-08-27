//,temp,RegionBroker.java,603,618,temp,RegionBroker.java,589,601
//,3
public class xxx {
    @Override
    public synchronized void removeBroker(Connection connection, BrokerInfo info) {
        if (info != null) {
            BrokerInfo existing = brokerInfos.get(info.getBrokerId());
            if (existing != null && existing.decrementRefCount() == 0) {
                brokerInfos.remove(info.getBrokerId());
            }
            LOG.debug("{} removeBroker: {} brokerInfo size: {}",
                    getBrokerName(), info.getBrokerName(), brokerInfos.size());
            // When stopping don't send cluster updates since we are the one's tearing down
            // our own bridges.
            if (!brokerService.isStopping()) {
                removeBrokerInClusterUpdate(info);
            }
        }
    }

};