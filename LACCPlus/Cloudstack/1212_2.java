//,temp,BasicNetworkTopology.java,446,465,temp,BasicNetworkTopology.java,166,186
//,3
public class xxx {
    @Override
    public boolean applyUserData(final Network network, final NicProfile nic, final VirtualMachineProfile profile, final DeployDestination dest, final DomainRouterVO router)
            throws ResourceUnavailableException {

        s_logger.debug("APPLYING USERDATA RULES");

        final String typeString = "userdata and password entry";
        final Long podId = dest.getPod().getId();
        boolean isPodLevelException = false;

        if (podId != null && profile.getVirtualMachine().getType() == VirtualMachine.Type.User && network.getTrafficType() == TrafficType.Guest
                && network.getGuestType() == Network.GuestType.Shared) {
            isPodLevelException = true;
        }

        final boolean failWhenDisconnect = false;

        final UserdataPwdRules pwdRules = new UserdataPwdRules(network, nic, profile, dest);

        return applyRules(network, router, typeString, isPodLevelException, podId, failWhenDisconnect, new RuleApplierWrapper<RuleApplier>(pwdRules));
    }

};