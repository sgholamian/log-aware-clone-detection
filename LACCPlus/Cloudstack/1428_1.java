//,temp,NiciraNvpGuestNetworkGuru.java,170,244,temp,OpendaylightGuestNetworkGuru.java,128,179
//,3
public class xxx {
    @Override
    public Network implement(final Network network, final NetworkOffering offering, final DeployDestination dest, final ReservationContext context)
            throws InsufficientVirtualNetworkCapacityException {
        assert network.getState() == State.Implementing : "Why are we implementing " + network;

        final long dcId = dest.getDataCenter().getId();

        Long physicalNetworkId = network.getPhysicalNetworkId();

        // physical network id can be null in Guest Network in Basic zone, so locate the physical network
        if (physicalNetworkId == null) {
            physicalNetworkId = networkModel.findPhysicalNetworkId(dcId, offering.getTags(), offering.getTrafficType());
        }

        final NetworkVO implemented = new NetworkVO(network.getTrafficType(), network.getMode(), network.getBroadcastDomainType(), network.getNetworkOfferingId(),
                State.Allocated, network.getDataCenterId(), physicalNetworkId, offering.isRedundantRouter());

        if (network.getGateway() != null) {
            implemented.setGateway(network.getGateway());
        }

        if (network.getCidr() != null) {
            implemented.setCidr(network.getCidr());
        }

        // Name is either the given name or the uuid
        String name = network.getName();
        if (name == null || name.isEmpty()) {
            name = ((NetworkVO) network).getUuid();
        }
        if (name.length() > MAX_NAME_LENGTH) {
            name = name.substring(0, MAX_NAME_LENGTH - 1);
        }

        final List<NiciraNvpDeviceVO> devices = niciraNvpDao.listByPhysicalNetwork(physicalNetworkId);
        if (devices.isEmpty()) {
            s_logger.error("No NiciraNvp Controller on physical network " + physicalNetworkId);
            return null;
        }
        final NiciraNvpDeviceVO niciraNvpDevice = devices.get(0);
        final HostVO niciraNvpHost = hostDao.findById(niciraNvpDevice.getHostId());
        hostDao.loadDetails(niciraNvpHost);
        final String transportzoneuuid = niciraNvpHost.getDetail("transportzoneuuid");
        final String transportzoneisotype = niciraNvpHost.getDetail("transportzoneisotype");

        if (offering.getGuestType().equals(GuestType.Shared)) {
            try {
                checkL2GatewayServiceSharedNetwork(niciraNvpHost);
            }
            catch (Exception e){
                s_logger.error("L2 Gateway Service Issue: " + e.getMessage());
                return null;
            }
        }

        final CreateLogicalSwitchCommand cmd = new CreateLogicalSwitchCommand(transportzoneuuid, transportzoneisotype, name, context.getDomain().getName() + "-"
                + context.getAccount().getAccountName());
        final CreateLogicalSwitchAnswer answer = (CreateLogicalSwitchAnswer) agentMgr.easySend(niciraNvpHost.getId(), cmd);

        if (answer == null || !answer.getResult()) {
            s_logger.error("CreateLogicalSwitchCommand failed");
            return null;
        }

        try {
            implemented.setBroadcastUri(new URI("lswitch", answer.getLogicalSwitchUuid(), null));
            implemented.setBroadcastDomainType(BroadcastDomainType.Lswitch);
            s_logger.info("Implemented OK, network linked to  = " + implemented.getBroadcastUri().toString());
        } catch (final URISyntaxException e) {
            s_logger.error("Unable to store logical switch id in broadcast uri, uuid = " + implemented.getUuid(), e);
            return null;
        }

        return implemented;
    }

};