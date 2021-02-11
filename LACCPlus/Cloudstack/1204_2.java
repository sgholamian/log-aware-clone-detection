//,temp,BrocadeVcsGuestNetworkGuru.java,159,187,temp,BrocadeVcsGuestNetworkGuru.java,120,157
//,3
public class xxx {
    @Override
    public Network implement(Network network, NetworkOffering offering, DeployDestination dest, ReservationContext context) throws InsufficientVirtualNetworkCapacityException {
        assert (network.getState() == State.Implementing) : "Why are we implementing " + network;

        Network implemented = super.implement(network, offering, dest, context);

        int vlanTag = Integer.parseInt(BroadcastDomainType.getValue(implemented.getBroadcastUri()));

        //get physical network id
        Long physicalNetworkId = network.getPhysicalNetworkId();

        List<BrocadeVcsDeviceVO> devices = _brocadeVcsDao.listByPhysicalNetwork(physicalNetworkId);
        if (devices.isEmpty()) {
            s_logger.error("No Brocade VCS Switch on physical network " + physicalNetworkId);
            return null;
        }

        for (BrocadeVcsDeviceVO brocadeVcsDevice : devices) {
            HostVO brocadeVcsHost = _hostDao.findById(brocadeVcsDevice.getHostId());

            // create createNetworkCmd instance and agentMgr execute it.
            CreateNetworkCommand cmd = new CreateNetworkCommand(vlanTag, network.getId(), context.getDomain().getName() + "-" + context.getAccount().getAccountName());
            CreateNetworkAnswer answer = (CreateNetworkAnswer)_agentMgr.easySend(brocadeVcsHost.getId(), cmd);

            if (answer == null || !answer.getResult()) {
                s_logger.error("CreateNetworkCommand failed");
                s_logger.error("Unable to create network " + network.getId());
                return null;
            }

        }

        // Persist the network-vlan mapping from db
        BrocadeVcsNetworkVlanMappingVO brocadeVcsNetworkVlanMapping = new BrocadeVcsNetworkVlanMappingVO(network.getId(), vlanTag);
        _brocadeVcsNetworkVlanDao.persist(brocadeVcsNetworkVlanMapping);

        return implemented;
    }

};