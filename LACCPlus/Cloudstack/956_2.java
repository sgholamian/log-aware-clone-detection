//,temp,BasicNetworkTopology.java,248,266,temp,AdvancedNetworkTopology.java,190,221
//,3
public class xxx {
    @Override
    public boolean associatePublicIP(final Network network, final List<? extends PublicIpAddress> ipAddresses, final VirtualRouter router)
            throws ResourceUnavailableException {

        if (ipAddresses == null || ipAddresses.isEmpty()) {
            s_logger.debug("No ip association rules to be applied for network " + network.getId());
            return true;
        }

        if (network.getVpcId() == null) {
            return super.associatePublicIP(network, ipAddresses, router);
        }

        s_logger.debug("APPLYING VPC IP RULES");

        final String typeString = "vpc ip association";
        final boolean isPodLevelException = false;
        final boolean failWhenDisconnect = false;
        final Long podId = null;

        final NicPlugInOutRules nicPlugInOutRules = new NicPlugInOutRules(network, ipAddresses);
        nicPlugInOutRules.accept(_advancedVisitor, router);

        final VpcIpAssociationRules ipAssociationRules = new VpcIpAssociationRules(network, ipAddresses);
        final boolean result = applyRules(network, router, typeString, isPodLevelException, podId, failWhenDisconnect, new RuleApplierWrapper<RuleApplier>(ipAssociationRules));

        if (result) {
            _advancedVisitor.visit(nicPlugInOutRules);
        }

        return result;
    }

};