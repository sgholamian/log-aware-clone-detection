//,temp,VirtualNetworkApplianceManagerImpl.java,2120,2164,temp,VpcVirtualNetworkApplianceManagerImpl.java,725,760
//,3
public class xxx {
    @Override
    public boolean startRemoteAccessVpn(final RemoteAccessVpn vpn, final VirtualRouter router) throws ResourceUnavailableException {
        if (router.getState() != State.Running) {
            s_logger.warn("Unable to apply remote access VPN configuration, virtual router is not in the right state " + router.getState());
            throw new ResourceUnavailableException("Unable to apply remote access VPN configuration," + " virtual router is not in the right state", DataCenter.class,
                    router.getDataCenterId());
        }

        final Commands cmds = new Commands(Command.OnError.Stop);
        _commandSetupHelper.createApplyVpnCommands(true, vpn, router, cmds);

        try {
            _agentMgr.send(router.getHostId(), cmds);
        } catch (final OperationTimedoutException e) {
            s_logger.debug("Failed to start remote access VPN: ", e);
            throw new AgentUnavailableException("Unable to send commands to virtual router ", router.getHostId(), e);
        }
        Answer answer = cmds.getAnswer("users");
        if (answer == null || !answer.getResult()) {
            String errorMessage = (answer == null) ? "null answer object" : answer.getDetails();
            s_logger.error("Unable to start vpn: unable add users to vpn in zone " + router.getDataCenterId() + " for account " + vpn.getAccountId() + " on domR: "
                    + router.getInstanceName() + " due to " + errorMessage);
            throw new ResourceUnavailableException("Unable to start vpn: Unable to add users to vpn in zone " + router.getDataCenterId() + " for account " + vpn.getAccountId()
            + " on domR: " + router.getInstanceName() + " due to " + errorMessage, DataCenter.class, router.getDataCenterId());
        }
        answer = cmds.getAnswer("startVpn");
        if (answer == null || !answer.getResult()) {
            String errorMessage = (answer == null) ? "null answer object" : answer.getDetails();
            s_logger.error("Unable to start vpn in zone " + router.getDataCenterId() + " for account " + vpn.getAccountId() + " on domR: " + router.getInstanceName() + " due to "
                    + errorMessage);
            throw new ResourceUnavailableException("Unable to start vpn in zone " + router.getDataCenterId() + " for account " + vpn.getAccountId() + " on domR: "
                    + router.getInstanceName() + " due to " + errorMessage, DataCenter.class, router.getDataCenterId());
        }

        return true;
    }

};