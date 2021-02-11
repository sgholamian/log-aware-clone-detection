//,temp,BasicNetworkTopology.java,341,354,temp,AdvancedNetworkTopology.java,143,157
//,3
public class xxx {
    @Override
    public boolean saveUserDataToRouter(final Network network, final NicProfile nic, final VirtualMachineProfile profile, final VirtualRouter router)
            throws ResourceUnavailableException {
        s_logger.debug("SAVE USERDATA TO ROUTE RULES");

        final String typeString = "save userdata entry";
        final boolean isPodLevelException = false;
        final boolean failWhenDisconnect = false;
        final Long podId = null;

        final UserdataToRouterRules userdataToRouterRules = new UserdataToRouterRules(network, nic, profile);

        return applyRules(network, router, typeString, isPodLevelException, podId, failWhenDisconnect, new RuleApplierWrapper<RuleApplier>(userdataToRouterRules));
    }

};