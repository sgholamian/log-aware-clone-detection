//,temp,BrocadeVcsGuestNetworkGuru.java,189,213,temp,OpendaylightGuestNetworkGuru.java,214,240
//,3
public class xxx {
    @Override
    public void deallocate(Network network, NicProfile nic, VirtualMachineProfile vm) {

        String interfaceMac = nic.getMacAddress();

        List<BrocadeVcsDeviceVO> devices = _brocadeVcsDao.listByPhysicalNetwork(network.getPhysicalNetworkId());
        if (devices.isEmpty()) {
            s_logger.error("No Brocade VCS Switch on physical network " + network.getPhysicalNetworkId());
            return;
        }
        for (BrocadeVcsDeviceVO brocadeVcsDevice : devices) {
            HostVO brocadeVcsHost = _hostDao.findById(brocadeVcsDevice.getHostId());

            // create DisassociateMacFromNetworkCmd instance and agentMgr execute it.
            DisassociateMacFromNetworkCommand cmd = new DisassociateMacFromNetworkCommand(network.getId(), interfaceMac);
            DisassociateMacFromNetworkAnswer answer = (DisassociateMacFromNetworkAnswer)_agentMgr.easySend(brocadeVcsHost.getId(), cmd);

            if (answer == null || !answer.getResult()) {
                s_logger.error("DisassociateMacFromNetworkCommand failed");
                s_logger.error("Unable to disassociate mac " + interfaceMac + " from network " + network.getId());
                return;
            }
        }
        super.deallocate(network, nic, vm);
    }

};