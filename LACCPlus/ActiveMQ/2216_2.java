//,temp,RegionBroker.java,603,618,temp,RegionBroker.java,589,601
//,3
public class xxx {
    @Override
    public synchronized void addBroker(Connection connection, BrokerInfo info) {
        BrokerInfo existing = brokerInfos.get(info.getBrokerId());
        if (existing == null) {
            existing = info.copy();
            existing.setPeerBrokerInfos(null);
            brokerInfos.put(info.getBrokerId(), existing);
        }
        existing.incrementRefCount();
        LOG.debug("{} addBroker: {} brokerInfo size: {}",
                getBrokerName(), info.getBrokerName(), brokerInfos.size());
        addBrokerInClusterUpdate(info);
    }

};