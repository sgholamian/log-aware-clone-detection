//,temp,BasicNetworkTopology.java,446,465,temp,BasicNetworkTopology.java,141,164
//,3
public class xxx {
    @Override
    public boolean applyDhcpEntry(final Network network, final NicProfile nic, final VirtualMachineProfile profile, final DeployDestination dest,
            final DomainRouterVO router) throws ResourceUnavailableException {

        s_logger.debug("APPLYING DHCP ENTRY RULES");

        final String typeString = "dhcp entry";
        final Long podId = dest.getPod().getId();
        boolean isPodLevelException = false;

        // for user vm in Basic zone we should try to re-deploy vm in a diff pod
        // if it fails to deploy in original pod; so throwing exception with Pod
        // scope
        if (podId != null && profile.getVirtualMachine().getType() == VirtualMachine.Type.User && network.getTrafficType() == TrafficType.Guest
                && network.getGuestType() == Network.GuestType.Shared) {
            isPodLevelException = true;
        }

        final boolean failWhenDisconnect = false;

        final DhcpEntryRules dhcpRules = new DhcpEntryRules(network, nic, profile, dest);

        return applyRules(network, router, typeString, isPodLevelException, podId, failWhenDisconnect, new RuleApplierWrapper<RuleApplier>(dhcpRules));
    }

};