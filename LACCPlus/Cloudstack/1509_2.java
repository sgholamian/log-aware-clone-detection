//,temp,BasicNetworkTopology.java,341,354,temp,BasicNetworkTopology.java,310,324
//,2
public class xxx {
    @Override
    public boolean savePasswordToRouter(final Network network, final NicProfile nic, final VirtualMachineProfile profile, final VirtualRouter router)
            throws ResourceUnavailableException {

        s_logger.debug("SAVE PASSWORD TO ROUTE RULES");

        final String typeString = "save password entry";
        final boolean isPodLevelException = false;
        final boolean failWhenDisconnect = false;
        final Long podId = null;

        final PasswordToRouterRules routerRules = new PasswordToRouterRules(network, nic, profile);

        return applyRules(network, router, typeString, isPodLevelException, podId, failWhenDisconnect, new RuleApplierWrapper<RuleApplier>(routerRules));
    }

};