//,temp,BrocadeVcsGuestNetworkGuru.java,226,262,temp,BrocadeVcsGuestNetworkGuru.java,189,213
//,3
public class xxx {
    @Override
    public boolean trash(Network network, NetworkOffering offering) {

        int vlanTag = 0;
        // Get the network-vlan mapping from db
        BrocadeVcsNetworkVlanMappingVO brocadeVcsNetworkVlanMapping = _brocadeVcsNetworkVlanDao.findByNetworkId(network.getId());

        if (brocadeVcsNetworkVlanMapping != null) {
            vlanTag = brocadeVcsNetworkVlanMapping.getVlanId();
        } else {
            s_logger.error("Not able to find vlanId for network " + network.getId());
            return false;
        }

        List<BrocadeVcsDeviceVO> devices = _brocadeVcsDao.listByPhysicalNetwork(network.getPhysicalNetworkId());
        if (devices.isEmpty()) {
            s_logger.error("No Brocade VCS Switch on physical network " + network.getPhysicalNetworkId());
            return false;
        }
        for (BrocadeVcsDeviceVO brocadeVcsDevice : devices) {
            HostVO brocadeVcsHost = _hostDao.findById(brocadeVcsDevice.getHostId());

            // create deleteNetworkCmd instance and agentMgr execute it.
            DeleteNetworkCommand cmd = new DeleteNetworkCommand(vlanTag, network.getId());
            DeleteNetworkAnswer answer = (DeleteNetworkAnswer)_agentMgr.easySend(brocadeVcsHost.getId(), cmd);

            if (answer == null || !answer.getResult()) {
                s_logger.error("DeleteNetworkCommand failed");
                s_logger.error("Unable to delete network " + network.getId());
                return false;
            }
        }

        // Remove the network-vlan mapping from db
        _brocadeVcsNetworkVlanDao.remove(brocadeVcsNetworkVlanMapping.getId());
        return super.trash(network, offering);
    }

};