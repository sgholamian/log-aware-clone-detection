//,temp,BasicNetworkTopology.java,326,339,temp,AdvancedNetworkTopology.java,175,188
//,3
public class xxx {
    @Override
    public boolean saveSSHPublicKeyToRouter(final Network network, final NicProfile nic, final VirtualMachineProfile profile, final VirtualRouter router,
            final String sshPublicKey) throws ResourceUnavailableException {
        s_logger.debug("SAVE SSH PUB KEY TO ROUTE RULES");

        final String typeString = "save SSHkey entry";
        final boolean isPodLevelException = false;
        final boolean failWhenDisconnect = false;
        final Long podId = null;

        final SshKeyToRouterRules keyToRouterRules = new SshKeyToRouterRules(network, nic, profile, sshPublicKey);

        return applyRules(network, router, typeString, isPodLevelException, podId, failWhenDisconnect, new RuleApplierWrapper<RuleApplier>(keyToRouterRules));
    }

};