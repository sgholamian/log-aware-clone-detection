//,temp,NetworkModelImpl.java,1710,1743,temp,NetworkModelImpl.java,1281,1314
//,2
public class xxx {
    @Override
    public String getDefaultStorageTrafficLabel(long zoneId, HypervisorType hypervisorType) {
        try {
            PhysicalNetwork storagePhyNetwork = getDefaultPhysicalNetworkByZoneAndTrafficType(zoneId, TrafficType.Storage);
            PhysicalNetworkTrafficTypeVO storageTraffic = _pNTrafficTypeDao.findBy(storagePhyNetwork.getId(), TrafficType.Storage);
            if (storageTraffic != null) {
                String label = null;
                switch (hypervisorType) {
                    case XenServer:
                        label = storageTraffic.getXenNetworkLabel();
                        break;
                    case KVM:
                        label = storageTraffic.getKvmNetworkLabel();
                        break;
                    case VMware:
                        label = storageTraffic.getVmwareNetworkLabel();
                        break;
                    case Hyperv:
                        label = storageTraffic.getHypervNetworkLabel();
                        break;
                    case Ovm3:
                        label = storageTraffic.getOvm3NetworkLabel();
                        break;
                }
                return label;
            }
        } catch (Exception ex) {
            if (s_logger.isDebugEnabled()) {
                s_logger.debug("Failed to retrive the default label for storage traffic:" + "zone: " + zoneId + " hypervisor: " + hypervisorType + " due to:" +
                    ex.getMessage());
            }
        }
        return null;
    }

};