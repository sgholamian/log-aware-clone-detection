//,temp,AdvancedNetworkTopology.java,90,118,temp,VpcVirtualNetworkApplianceManagerImpl.java,762,778
//,3
public class xxx {
    @Override
    public boolean stopRemoteAccessVpn(final RemoteAccessVpn vpn, final VirtualRouter router) throws ResourceUnavailableException {
        boolean result = true;

        if (router.getState() == State.Running) {
            final Commands cmds = new Commands(Command.OnError.Continue);
            _commandSetupHelper.createApplyVpnCommands(false, vpn, router, cmds);
            result = result && _nwHelper.sendCommandsToRouter(router, cmds);
        } else if (router.getState() == State.Stopped) {
            s_logger.debug("Router " + router + " is in Stopped state, not sending deleteRemoteAccessVpn command to it");
        } else {
            s_logger.warn("Failed to stop remote access VPN: domR " + router + " is not in right state " + router.getState());
            throw new ResourceUnavailableException("Failed to stop remote access VPN: domR is not in right state " + router.getState(), DataCenter.class,
                    router.getDataCenterId());
        }
        return true;
    }

};