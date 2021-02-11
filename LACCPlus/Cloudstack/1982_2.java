//,temp,VirtualNetworkApplianceManagerImpl.java,2166,2190,temp,VpcVirtualNetworkApplianceManagerImpl.java,208,234
//,3
public class xxx {
    protected boolean setupVpcGuestNetwork(final Network network, final VirtualRouter router, final boolean add, final NicProfile guestNic) throws ConcurrentOperationException,
    ResourceUnavailableException {

        boolean result = true;
        if (router.getState() == State.Running) {
            final SetupGuestNetworkCommand setupCmd = _commandSetupHelper.createSetupGuestNetworkCommand((DomainRouterVO) router, add, guestNic);

            final Commands cmds = new Commands(Command.OnError.Stop);
            cmds.addCommand("setupguestnetwork", setupCmd);
            _nwHelper.sendCommandsToRouter(router, cmds);

            final Answer setupAnswer = cmds.getAnswer("setupguestnetwork");
            final String setup = add ? "set" : "destroy";
            if (!(setupAnswer != null && setupAnswer.getResult())) {
                s_logger.warn("Unable to " + setup + " guest network on router " + router);
                result = false;
            }
            return result;
        } else if (router.getState() == State.Stopped || router.getState() == State.Stopping) {
            s_logger.debug("Router " + router.getInstanceName() + " is in " + router.getState() + ", so not sending setup guest network command to the backend");
            return true;
        } else {
            s_logger.warn("Unable to setup guest network on virtual router " + router + " is not in the right state " + router.getState());
            throw new ResourceUnavailableException("Unable to setup guest network on the backend," + " virtual router " + router + " is not in the right state", DataCenter.class,
                    router.getDataCenterId());
        }
    }

};