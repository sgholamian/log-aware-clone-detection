//,temp,NetscalerElement.java,1266,1319,temp,NetscalerElement.java,1089,1155
//,3
public class xxx {
    public boolean applyElasticLoadBalancerRules(Network network, List<LoadBalancingRule> loadBalancingRules)
            throws ResourceUnavailableException {

        if (loadBalancingRules == null || loadBalancingRules.isEmpty()) {
            return true;
        }

        String errMsg = null;
        ExternalLoadBalancerDeviceVO lbDeviceVO = getExternalLoadBalancerForNetwork(network);
        if (lbDeviceVO == null) {
            try {
                lbDeviceVO = allocateLoadBalancerForNetwork(network);
            } catch (Exception e) {
                errMsg = "Could not allocate a NetSclaer load balancer for configuring elastic load balancer rules due to "
                        + e.getMessage();
                s_logger.error(errMsg);
                throw new ResourceUnavailableException(errMsg, this.getClass(), 0);
            }
        }

        if (!isNetscalerDevice(lbDeviceVO.getDeviceName())) {
            errMsg = "There are no NetScaler load balancer assigned for this network. So NetScaler element can not be handle elastic load balancer rules.";
            s_logger.error(errMsg);
            throw new ResourceUnavailableException(errMsg, this.getClass(), 0);
        }

        List<LoadBalancerTO> loadBalancersToApply = new ArrayList<LoadBalancerTO>();
        for (int i = 0; i < loadBalancingRules.size(); i++) {
            LoadBalancingRule rule = loadBalancingRules.get(i);
            boolean revoked = (rule.getState().equals(FirewallRule.State.Revoke));
            String protocol = rule.getProtocol();
            String algorithm = rule.getAlgorithm();
            String lbUuid = rule.getUuid();
            String srcIp = rule.getSourceIp().addr();
            int srcPort = rule.getSourcePortStart();
            List<LbDestination> destinations = rule.getDestinations();

            if ((destinations != null && !destinations.isEmpty()) || rule.isAutoScaleConfig()) {
                LoadBalancerTO loadBalancer = new LoadBalancerTO(lbUuid, srcIp, srcPort, protocol, algorithm, revoked,
                        false, false, destinations, rule.getStickinessPolicies(), rule.getHealthCheckPolicies(),
                        rule.getLbSslCert(), rule.getLbProtocol());
                if (rule.isAutoScaleConfig()) {
                    loadBalancer.setAutoScaleVmGroup(rule.getAutoScaleVmGroup());
                }
                loadBalancersToApply.add(loadBalancer);
            }
        }

        if (loadBalancersToApply.size() > 0) {
            int numLoadBalancersForCommand = loadBalancersToApply.size();
            LoadBalancerTO[] loadBalancersForCommand = loadBalancersToApply
                    .toArray(new LoadBalancerTO[numLoadBalancersForCommand]);
            LoadBalancerConfigCommand cmd = new LoadBalancerConfigCommand(loadBalancersForCommand, null);

            HostVO externalLoadBalancer = _hostDao.findById(lbDeviceVO.getHostId());
            Answer answer = _agentMgr.easySend(externalLoadBalancer.getId(), cmd);
            if (answer == null || !answer.getResult()) {
                String details = (answer != null) ? answer.getDetails() : "details unavailable";
                String msg = "Unable to apply elastic load balancer rules to the external load balancer appliance in zone "
                        + network.getDataCenterId() + " due to: " + details + ".";
                s_logger.error(msg);
                throw new ResourceUnavailableException(msg, DataCenter.class, network.getDataCenterId());
            }
        }

        return true;
    }

};