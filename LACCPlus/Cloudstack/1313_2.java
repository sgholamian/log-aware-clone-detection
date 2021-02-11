//,temp,VpcVirtualNetworkApplianceManagerImpl.java,522,556,temp,AdvancedNetworkVisitor.java,130,172
//,3
public class xxx {
    @Override
    public boolean visit(final PrivateGatewayRules privateGW) throws ResourceUnavailableException {
        final VirtualRouter router = privateGW.getRouter();
        final NicProfile nicProfile = privateGW.getNicProfile();

        final boolean isAddOperation = privateGW.isAddOperation();

        if (router.getState() == State.Running) {

            final PrivateIpVO ipVO = privateGW.retrivePrivateIP(this);
            final Network network = privateGW.retrievePrivateNetwork(this);

            final String netmask = NetUtils.getCidrNetmask(network.getCidr());
            final PrivateIpAddress ip = new PrivateIpAddress(ipVO, network.getBroadcastUri().toString(), network.getGateway(), netmask, nicProfile.getMacAddress());

            final List<PrivateIpAddress> privateIps = new ArrayList<PrivateIpAddress>(1);
            privateIps.add(ip);

            final Commands cmds = new Commands(Command.OnError.Stop);
            _commandSetupHelper.createVpcAssociatePrivateIPCommands(router, privateIps, cmds, isAddOperation);

            try {
                if (_networkGeneralHelper.sendCommandsToRouter(router, cmds)) {
                    s_logger.debug("Successfully applied ip association for ip " + ip + " in vpc network " + network);
                    return true;
                } else {
                    s_logger.warn("Failed to associate ip address " + ip + " in vpc network " + network);
                    return false;
                }
            } catch (final Exception ex) {
                s_logger.warn("Failed to send  " + (isAddOperation ? "add " : "delete ") + " private network " + network + " commands to rotuer ");
                return false;
            }
        } else if (router.getState() == State.Stopped || router.getState() == State.Stopping) {
            s_logger.debug("Router " + router.getInstanceName() + " is in " + router.getState() + ", so not sending setup private network command to the backend");
        } else {
            s_logger.warn("Unable to setup private gateway, virtual router " + router + " is not in the right state " + router.getState());

            throw new ResourceUnavailableException("Unable to setup Private gateway on the backend," + " virtual router " + router + " is not in the right state",
                    DataCenter.class, router.getDataCenterId());
        }
        return true;
    }

};