//,temp,BasicNetworkTopology.java,446,465,temp,BasicNetworkTopology.java,141,164
//,3
public class xxx {
    @Override
    public boolean removeDhcpEntry(Network network, NicProfile nic, VirtualMachineProfile profile, VirtualRouter virtualRouter) throws ResourceUnavailableException {
        s_logger.debug("REMOVING DHCP ENTRY RULE");

        final String typeString = "dhcp entry";
        final Long podId = profile.getVirtualMachine().getPodIdToDeployIn();
        boolean isPodLevelException = false;

        if (podId != null && profile.getVirtualMachine().getType() == VirtualMachine.Type.User && network.getTrafficType() == TrafficType.Guest
                && network.getGuestType() == Network.GuestType.Shared) {
            isPodLevelException = true;
        }

        final boolean failWhenDisconnect = false;

        final DhcpEntryRules dhcpRules = new DhcpEntryRules(network, nic, profile, null);
        dhcpRules.setRemove(true);

        return applyRules(network, virtualRouter, typeString, isPodLevelException, podId, failWhenDisconnect, new RuleApplierWrapper<RuleApplier>(dhcpRules));
    }

};