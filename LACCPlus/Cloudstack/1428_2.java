//,temp,NiciraNvpGuestNetworkGuru.java,170,244,temp,OpendaylightGuestNetworkGuru.java,128,179
//,3
public class xxx {
    @Override
    public Network implement(Network network, NetworkOffering offering, DeployDestination dest, ReservationContext context) throws InsufficientVirtualNetworkCapacityException {
        assert (network.getState() == State.Implementing) : "Why are we implementing " + network;

        long dcId = dest.getDataCenter().getId();

        //get physical network id
        Long physicalNetworkId = network.getPhysicalNetworkId();

        // physical network id can be null in Guest Network in Basic zone, so locate the physical network
        if (physicalNetworkId == null) {
            physicalNetworkId = networkModel.findPhysicalNetworkId(dcId, offering.getTags(), offering.getTrafficType());
        }

        NetworkVO implemented = new NetworkVO(network.getTrafficType(), network.getMode(), network.getBroadcastDomainType(), network.getNetworkOfferingId(), State.Allocated,
                network.getDataCenterId(), physicalNetworkId, offering.isRedundantRouter());

        if (network.getGateway() != null) {
            implemented.setGateway(network.getGateway());
        }

        if (network.getCidr() != null) {
            implemented.setCidr(network.getCidr());
        }

        // Name is either the given name or the uuid
        String name = network.getName();
        if (name == null || name.isEmpty()) {
            name = ((NetworkVO)network).getUuid();
        }

        List<OpenDaylightControllerVO> devices = openDaylightControllerMappingDao.listByPhysicalNetwork(physicalNetworkId);
        if (devices.isEmpty()) {
            s_logger.error("No Controller on physical network " + physicalNetworkId);
            return null;
        }
        OpenDaylightControllerVO controller = devices.get(0);

        ConfigureNetworkCommand cmd = new ConfigureNetworkCommand(name, context.getAccount().getAccountName());
        ConfigureNetworkAnswer answer = (ConfigureNetworkAnswer)agentManager.easySend(controller.getHostId(), cmd);

        if (answer == null || !answer.getResult()) {
            s_logger.error("ConfigureNetworkCommand failed");
            return null;
        }

        implemented.setBroadcastUri(BroadcastDomainType.OpenDaylight.toUri(answer.getNetworkUuid()));
        implemented.setBroadcastDomainType(BroadcastDomainType.OpenDaylight);
        s_logger.info("Implemented OK, network linked to  = " + implemented.getBroadcastUri().toString());

        return implemented;
    }

};