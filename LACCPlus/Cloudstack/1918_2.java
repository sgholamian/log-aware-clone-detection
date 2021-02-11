//,temp,BrocadeVcsGuestNetworkGuru.java,189,213,temp,BrocadeVcsGuestNetworkGuru.java,159,187
//,3
public class xxx {
    @Override
    public void reserve(NicProfile nic, Network network, VirtualMachineProfile vm, DeployDestination dest, ReservationContext context)
            throws InsufficientVirtualNetworkCapacityException, InsufficientAddressCapacityException {
        super.reserve(nic, network, vm, dest, context);

        DataCenter dc = _dcDao.findById(network.getDataCenterId());

        String interfaceMac = nic.getMacAddress();

        List<BrocadeVcsDeviceVO> devices = _brocadeVcsDao.listByPhysicalNetwork(network.getPhysicalNetworkId());
        if (devices.isEmpty()) {
            s_logger.error("No Brocade VCS Switch on physical network " + network.getPhysicalNetworkId());
            return;
        }
        for (BrocadeVcsDeviceVO brocadeVcsDevice : devices) {
            HostVO brocadeVcsHost = _hostDao.findById(brocadeVcsDevice.getHostId());

            // create AssociateMacToNetworkCmd instance and agentMgr execute it.
            AssociateMacToNetworkCommand cmd = new AssociateMacToNetworkCommand(network.getId(), interfaceMac, context.getDomain().getName() + "-"
                    + context.getAccount().getAccountName());
            AssociateMacToNetworkAnswer answer = (AssociateMacToNetworkAnswer)_agentMgr.easySend(brocadeVcsHost.getId(), cmd);

            if (answer == null || !answer.getResult()) {
                s_logger.error("AssociateMacToNetworkCommand failed");
                throw new InsufficientVirtualNetworkCapacityException("Unable to associate mac " + interfaceMac + " to network " + network.getId(), DataCenter.class, dc.getId());
            }
        }

    }

};