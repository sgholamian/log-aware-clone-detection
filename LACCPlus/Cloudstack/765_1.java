//,temp,NetworkModelImpl.java,1710,1743,temp,NetworkModelImpl.java,1246,1279
//,2
public class xxx {
    @Override
    public String getDefaultGuestTrafficLabel(long dcId, HypervisorType hypervisorType) {
        try {
            PhysicalNetwork guestPhyNetwork = getOnePhysicalNetworkByZoneAndTrafficType(dcId, TrafficType.Guest);
            PhysicalNetworkTrafficTypeVO guestTraffic = _pNTrafficTypeDao.findBy(guestPhyNetwork.getId(), TrafficType.Guest);
            if (guestTraffic != null) {
                String label = null;
                switch (hypervisorType) {
                    case XenServer:
                        label = guestTraffic.getXenNetworkLabel();
                        break;
                    case KVM:
                        label = guestTraffic.getKvmNetworkLabel();
                        break;
                    case VMware:
                        label = guestTraffic.getVmwareNetworkLabel();
                        break;
                    case Hyperv:
                        label = guestTraffic.getHypervNetworkLabel();
                        break;
                    case Ovm3:
                        label = guestTraffic.getOvm3NetworkLabel();
                        break;
                }
                return label;
            }
        } catch (Exception ex) {
            if (s_logger.isDebugEnabled()) {
                s_logger.debug("Failed to retrive the default label for guest traffic:" + "zone: " + dcId + " hypervisor: " + hypervisorType + " due to:" +
                    ex.getMessage());
            }
        }
        return null;
    }

};