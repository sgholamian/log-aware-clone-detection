//,temp,InternalLoadBalancerVMManagerImpl.java,863,886,temp,AdvancedNetworkTopology.java,90,118
//,3
public class xxx {
    @Override
    public boolean applyLoadBalancingRules(final Network network, final List<LoadBalancingRule> rules, final List<? extends VirtualRouter> internalLbVms)
            throws ResourceUnavailableException {
        if (rules == null || rules.isEmpty()) {
            s_logger.debug("No lb rules to be applied for network " + network);
            return true;
        }
        s_logger.info("lb rules to be applied for network ");
        //only one internal lb vm is supported per ip address at this time
        if (internalLbVms == null || internalLbVms.isEmpty()) {
            throw new CloudRuntimeException("Can't apply the lb rules on network " + network + " as the list of internal lb vms is empty");
        }

        final VirtualRouter lbVm = internalLbVms.get(0);
        if (lbVm.getState() == State.Running) {
            return sendLBRules(lbVm, rules, network.getId());
        } else if (lbVm.getState() == State.Stopped || lbVm.getState() == State.Stopping) {
            s_logger.debug("Internal LB VM " + lbVm.getInstanceName() + " is in " + lbVm.getState() + ", so not sending apply lb rules commands to the backend");
            return true;
        } else {
            s_logger.warn("Unable to apply lb rules, Internal LB VM is not in the right state " + lbVm.getState());
            throw new ResourceUnavailableException("Unable to apply lb rules; Internal LB VM is not in the right state", DataCenter.class, lbVm.getDataCenterId());
        }
    }

};