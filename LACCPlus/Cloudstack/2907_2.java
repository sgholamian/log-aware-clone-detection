//,temp,NetworkModelImpl.java,1710,1743,temp,NetworkModelImpl.java,1675,1708
//,2
public class xxx {
    @Override
    public String getDefaultPublicTrafficLabel(long dcId, HypervisorType hypervisorType) {
        try {
            PhysicalNetwork publicPhyNetwork = getOnePhysicalNetworkByZoneAndTrafficType(dcId, TrafficType.Public);
            PhysicalNetworkTrafficTypeVO publicTraffic = _pNTrafficTypeDao.findBy(publicPhyNetwork.getId(), TrafficType.Public);
            if (publicTraffic != null) {
                String label = null;
                switch (hypervisorType) {
                    case XenServer:
                        label = publicTraffic.getXenNetworkLabel();
                        break;
                    case KVM:
                        label = publicTraffic.getKvmNetworkLabel();
                        break;
                    case VMware:
                        label = publicTraffic.getVmwareNetworkLabel();
                        break;
                    case Hyperv:
                        label = publicTraffic.getHypervNetworkLabel();
                        break;
                    case Ovm3:
                        label = publicTraffic.getOvm3NetworkLabel();
                        break;
                }
                return label;
            }
        } catch (Exception ex) {
            if (s_logger.isDebugEnabled()) {
                s_logger.debug("Failed to retrieve the default label for public traffic." + "zone: " + dcId + " hypervisor: " + hypervisorType + " due to: " +
                    ex.getMessage());
            }
        }
        return null;
    }

};