//,temp,BasicNetworkTopology.java,326,339,temp,AdvancedNetworkTopology.java,175,188
//,3
public class xxx {
    @Override
    public boolean removeDhcpEntry(Network network, NicProfile nic, VirtualMachineProfile profile, VirtualRouter virtualRouter) throws ResourceUnavailableException {
        s_logger.debug("REMOVE VPC DHCP ENTRY RULES");

        final String typeString = "dhcp entry";
        final Long podId = null;
        final boolean isPodLevelException = false;
        final boolean failWhenDisconnect = false;

        final DhcpEntryRules dhcpRules = new DhcpEntryRules(network, nic, profile, null);
        dhcpRules.setRemove(true);

        return applyRules(network, virtualRouter, typeString, isPodLevelException, podId, failWhenDisconnect, new RuleApplierWrapper<RuleApplier>(dhcpRules));
    }

};