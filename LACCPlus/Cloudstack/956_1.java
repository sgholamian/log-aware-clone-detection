//,temp,BasicNetworkTopology.java,248,266,temp,AdvancedNetworkTopology.java,190,221
//,3
public class xxx {
    @Override
    public boolean associatePublicIP(final Network network, final List<? extends PublicIpAddress> ipAddress, final VirtualRouter router)
            throws ResourceUnavailableException {
        if (ipAddress == null || ipAddress.isEmpty()) {
            s_logger.debug("No ip association rules to be applied for network " + network.getId());
            return true;
        }

        s_logger.debug("APPLYING IP RULES");

        final String typeString = "ip association";
        final boolean isPodLevelException = false;
        final boolean failWhenDisconnect = false;
        final Long podId = null;

        final IpAssociationRules ipAddresses = new IpAssociationRules(network, ipAddress);

        return applyRules(network, router, typeString, isPodLevelException, podId, failWhenDisconnect, new RuleApplierWrapper<RuleApplier>(ipAddresses));
    }

};