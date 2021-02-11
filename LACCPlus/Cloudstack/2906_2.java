//,temp,NetworkModelImpl.java,1281,1314,temp,NetworkModelImpl.java,1246,1279
//,2
public class xxx {
    @Override
    public String getDefaultManagementTrafficLabel(long zoneId, HypervisorType hypervisorType) {
        try {
            PhysicalNetwork mgmtPhyNetwork = getDefaultPhysicalNetworkByZoneAndTrafficType(zoneId, TrafficType.Management);
            PhysicalNetworkTrafficTypeVO mgmtTraffic = _pNTrafficTypeDao.findBy(mgmtPhyNetwork.getId(), TrafficType.Management);
            if (mgmtTraffic != null) {
                String label = null;
                switch (hypervisorType) {
                    case XenServer:
                        label = mgmtTraffic.getXenNetworkLabel();
                        break;
                    case KVM:
                        label = mgmtTraffic.getKvmNetworkLabel();
                        break;
                    case VMware:
                        label = mgmtTraffic.getVmwareNetworkLabel();
                        break;
                    case Hyperv:
                        label = mgmtTraffic.getHypervNetworkLabel();
                        break;
                    case Ovm3:
                        label = mgmtTraffic.getOvm3NetworkLabel();
                        break;
                }
                return label;
            }
        } catch (Exception ex) {
            if (s_logger.isDebugEnabled()) {
                s_logger.debug("Failed to retrive the default label for management traffic:" + "zone: " + zoneId + " hypervisor: " + hypervisorType + " due to:" +
                    ex.getMessage());
            }
        }
        return null;
    }

};