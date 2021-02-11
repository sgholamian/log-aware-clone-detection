//,temp,BasicNetworkTopology.java,248,266,temp,AdvancedNetworkTopology.java,223,243
//,3
public class xxx {
    @Override
    public boolean applyNetworkACLs(final Network network, final List<? extends NetworkACLItem> rules, final VirtualRouter router, final boolean isPrivateGateway)
            throws ResourceUnavailableException {

        if (rules == null || rules.isEmpty()) {
            s_logger.debug("No network ACLs to be applied for network " + network.getId());
            return true;
        }

        s_logger.debug("APPLYING NETWORK ACLs RULES");

        final String typeString = "network acls";
        final boolean isPodLevelException = false;
        final boolean failWhenDisconnect = false;
        final Long podId = null;

        final NetworkAclsRules aclsRules = new NetworkAclsRules(network, rules, isPrivateGateway);

        final boolean result = applyRules(network, router, typeString, isPodLevelException, podId, failWhenDisconnect, new RuleApplierWrapper<RuleApplier>(aclsRules));
        return result;
    }

};