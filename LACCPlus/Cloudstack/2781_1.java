//,temp,VirtualNetworkApplianceManagerImpl.java,2120,2164,temp,VpcVirtualNetworkApplianceManagerImpl.java,725,760
//,3
public class xxx {
    @Override
    public boolean startRemoteAccessVpn(final Network network, final RemoteAccessVpn vpn, final List<? extends VirtualRouter> routers) throws ResourceUnavailableException {
        if (routers == null || routers.isEmpty()) {
            s_logger.warn("Failed to start remote access VPN: no router found for account and zone");
            throw new ResourceUnavailableException("Failed to start remote access VPN: no router found for account and zone", DataCenter.class, network.getDataCenterId());
        }

        for (final VirtualRouter router : routers) {
            if (router.getState() != VirtualMachine.State.Running) {
                s_logger.warn("Failed to start remote access VPN: router not in right state " + router.getState());
                throw new ResourceUnavailableException("Failed to start remote access VPN: router not in right state " + router.getState(), DataCenter.class,
                        network.getDataCenterId());
            }

            final Commands cmds = new Commands(Command.OnError.Stop);
            _commandSetupHelper.createApplyVpnCommands(true, vpn, router, cmds);

            if (!_nwHelper.sendCommandsToRouter(router, cmds)) {
                throw new AgentUnavailableException("Unable to send commands to virtual router ", router.getHostId());
            }

            Answer answer = cmds.getAnswer("users");
            if (answer == null) {
                s_logger.error("Unable to start vpn: unable add users to vpn in zone " + router.getDataCenterId() + " for account " + vpn.getAccountId() + " on domR: "
                        + router.getInstanceName() + " due to null answer");
                throw new ResourceUnavailableException("Unable to start vpn in zone " + router.getDataCenterId() + " for account " + vpn.getAccountId() + " on domR: "
                        + router.getInstanceName() + " due to null answer", DataCenter.class, router.getDataCenterId());
            }
            if (!answer.getResult()) {
                s_logger.error("Unable to start vpn: unable add users to vpn in zone " + router.getDataCenterId() + " for account " + vpn.getAccountId() + " on domR: "
                        + router.getInstanceName() + " due to " + answer.getDetails());
                throw new ResourceUnavailableException("Unable to start vpn: Unable to add users to vpn in zone " + router.getDataCenterId() + " for account "
                        + vpn.getAccountId() + " on domR: " + router.getInstanceName() + " due to " + answer.getDetails(), DataCenter.class, router.getDataCenterId());
            }
            answer = cmds.getAnswer("startVpn");
            if (!answer.getResult()) {
                s_logger.error("Unable to start vpn in zone " + router.getDataCenterId() + " for account " + vpn.getAccountId() + " on domR: " + router.getInstanceName()
                        + " due to " + answer.getDetails());
                throw new ResourceUnavailableException("Unable to start vpn in zone " + router.getDataCenterId() + " for account " + vpn.getAccountId() + " on domR: "
                        + router.getInstanceName() + " due to " + answer.getDetails(), DataCenter.class, router.getDataCenterId());
            }

        }
        return true;
    }

};