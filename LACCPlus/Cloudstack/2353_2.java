//,temp,BasicNetworkTopology.java,310,324,temp,AdvancedNetworkTopology.java,143,157
//,3
public class xxx {
    @Override
    public boolean applyUserData(final Network network, final NicProfile nic, final VirtualMachineProfile profile, final DeployDestination dest, final DomainRouterVO router)
            throws ResourceUnavailableException {

        s_logger.debug("APPLYING VPC USERDATA RULES");

        final String typeString = "userdata and password entry";
        final boolean isPodLevelException = false;
        final boolean failWhenDisconnect = false;
        final Long podId = null;

        final UserdataPwdRules pwdRules = new UserdataPwdRules(network, nic, profile, dest);

        return applyRules(network, router, typeString, isPodLevelException, podId, failWhenDisconnect, new RuleApplierWrapper<RuleApplier>(pwdRules));
    }

};