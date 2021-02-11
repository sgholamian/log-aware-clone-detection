//,temp,NetscalerElement.java,1266,1319,temp,NetscalerElement.java,1089,1155
//,3
public class xxx {
    public List<LoadBalancerTO> getElasticLBRulesHealthCheck(Network network,
            List<LoadBalancingRule> loadBalancingRules) throws ResourceUnavailableException {

        HealthCheckLBConfigAnswer answer = null;

        if (loadBalancingRules == null || loadBalancingRules.isEmpty()) {
            return null;
        }

        String errMsg = null;
        ExternalLoadBalancerDeviceVO lbDeviceVO = getExternalLoadBalancerForNetwork(network);

        if (lbDeviceVO == null) {
            s_logger.warn(
                    "There is no external load balancer device assigned to this network either network is not implement are already shutdown so just returning");
            return null;
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
                        false, false, destinations, null, rule.getHealthCheckPolicies(), rule.getLbSslCert(),
                        rule.getLbProtocol());
                loadBalancersToApply.add(loadBalancer);
            }
        }

        if (loadBalancersToApply.size() > 0) {
            int numLoadBalancersForCommand = loadBalancersToApply.size();
            LoadBalancerTO[] loadBalancersForCommand = loadBalancersToApply
                    .toArray(new LoadBalancerTO[numLoadBalancersForCommand]);
            HealthCheckLBConfigCommand cmd = new HealthCheckLBConfigCommand(loadBalancersForCommand, network.getId());
            HostVO externalLoadBalancer = _hostDao.findById(lbDeviceVO.getHostId());
            answer = (HealthCheckLBConfigAnswer)_agentMgr.easySend(externalLoadBalancer.getId(), cmd);
            return answer.getLoadBalancers();
        }
        return null;
    }

};