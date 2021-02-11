//,temp,VirtualNetworkApplianceManagerImpl.java,2166,2190,temp,VpcVirtualNetworkApplianceManagerImpl.java,208,234
//,3
public class xxx {
    @Override
    public boolean deleteRemoteAccessVpn(final Network network, final RemoteAccessVpn vpn, final List<? extends VirtualRouter> routers) throws ResourceUnavailableException {
        if (routers == null || routers.isEmpty()) {
            s_logger.warn("Failed to delete remote access VPN: no router found for account and zone");
            throw new ResourceUnavailableException("Failed to delete remote access VPN", DataCenter.class, network.getDataCenterId());
        }

        boolean result = true;
        for (final VirtualRouter router : routers) {
            if (router.getState() == VirtualMachine.State.Running) {
                final Commands cmds = new Commands(Command.OnError.Continue);
                _commandSetupHelper.createApplyVpnCommands(false, vpn, router, cmds);
                result = result && _nwHelper.sendCommandsToRouter(router, cmds);
            } else if (router.getState() == VirtualMachine.State.Stopped) {
                s_logger.debug("Router " + router + " is in Stopped state, not sending deleteRemoteAccessVpn command to it");
                continue;
            } else {
                s_logger.warn("Failed to delete remote access VPN: domR " + router + " is not in right state " + router.getState());
                throw new ResourceUnavailableException("Failed to delete remote access VPN: domR is not in right state " + router.getState(), DataCenter.class,
                        network.getDataCenterId());
            }
        }

        return result;
    }

};