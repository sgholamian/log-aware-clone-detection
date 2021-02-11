//,temp,ExternalLoadBalancerDeviceManagerImpl.java,1213,1297,temp,ExternalLoadBalancerDeviceManagerImpl.java,904,1029
//,3
public class xxx {
    @Override
    public List<LoadBalancerTO> getLBHealthChecks(Network network, List<LoadBalancingRule> loadBalancingRules) throws ResourceUnavailableException {

        // Find the external load balancer in this zone
        long zoneId = network.getDataCenterId();
        DataCenterVO zone = _dcDao.findById(zoneId);

        if (loadBalancingRules == null || loadBalancingRules.isEmpty()) {
            return null;
        }

        HostVO externalLoadBalancer = null;

        if(isNccServiceProvider(network)) {
            externalLoadBalancer  = getNetScalerControlCenterForNetwork(network);
        } else {
            ExternalLoadBalancerDeviceVO lbDeviceVO = getExternalLoadBalancerForNetwork(network);
            if (lbDeviceVO == null) {
                s_logger.warn("There is no external load balancer device assigned to this network either network is not implement are already shutdown so just returning");
                return null;
            } else {
                externalLoadBalancer = _hostDao.findById(lbDeviceVO.getHostId());
            }
        }

        boolean externalLoadBalancerIsInline = _networkMgr.isNetworkInlineMode(network);

        if (network.getState() == Network.State.Allocated) {
            s_logger.debug("External load balancer was asked to apply LB rules for network with ID " + network.getId() +
                "; this network is not implemented. Skipping backend commands.");
            return null;
        }

        List<LoadBalancerTO> loadBalancersToApply = new ArrayList<LoadBalancerTO>();
        List<MappingState> mappingStates = new ArrayList<MappingState>();
        for (final LoadBalancingRule rule : loadBalancingRules) {
            boolean revoked = (FirewallRule.State.Revoke.equals(rule.getState()));
            String protocol = rule.getProtocol();
            String algorithm = rule.getAlgorithm();
            String uuid = rule.getUuid();
            String srcIp = rule.getSourceIp().addr();
            int srcPort = rule.getSourcePortStart();
            List<LbDestination> destinations = rule.getDestinations();

            if (externalLoadBalancerIsInline) {
                long sourceIpId = _networkModel.getPublicIpAddress(rule.getSourceIp().addr(), network.getDataCenterId()).getId();
                MappingNic nic = getLoadBalancingIpNic(zone, network, sourceIpId, revoked, null);
                mappingStates.add(nic.getState());
                Nic loadBalancingIpNic = nic.getNic();
                if (loadBalancingIpNic == null) {
                    continue;
                }

                // Change the source IP address for the load balancing rule to
                // be the load balancing IP address
                srcIp = loadBalancingIpNic.getIPv4Address();
            }

            if ((destinations != null && !destinations.isEmpty()) || !rule.isAutoScaleConfig()) {
                boolean inline = _networkMgr.isNetworkInlineMode(network);
                LoadBalancerTO loadBalancer =
                    new LoadBalancerTO(uuid, srcIp, srcPort, protocol, algorithm, revoked, false, inline, destinations, rule.getStickinessPolicies(),
                        rule.getHealthCheckPolicies(), rule.getLbSslCert(), rule.getLbProtocol());
                loadBalancersToApply.add(loadBalancer);
            }
        }

        try {
            if (loadBalancersToApply.size() > 0) {
                int numLoadBalancersForCommand = loadBalancersToApply.size();
                LoadBalancerTO[] loadBalancersForCommand = loadBalancersToApply.toArray(new LoadBalancerTO[numLoadBalancersForCommand]);
                HealthCheckLBConfigCommand cmd = new HealthCheckLBConfigCommand(loadBalancersForCommand, network.getId());
                long guestVlanTag = Integer.parseInt(BroadcastDomainType.getValue(network.getBroadcastUri()));
                cmd.setAccessDetail(NetworkElementCommand.GUEST_VLAN_TAG, String.valueOf(guestVlanTag));

                HealthCheckLBConfigAnswer answer = (HealthCheckLBConfigAnswer) _agentMgr.easySend(externalLoadBalancer.getId(), cmd);
                // easySend will return null on error
                return answer == null ? null : answer.getLoadBalancers();
            }
        } catch (Exception ex) {
            s_logger.error("Exception Occured ", ex);
        }
        //null return is handled by clients
        return null;
    }

};