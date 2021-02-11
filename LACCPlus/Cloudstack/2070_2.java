//,temp,ExternalLoadBalancerDeviceManagerImpl.java,1213,1297,temp,ExternalLoadBalancerDeviceManagerImpl.java,904,1029
//,3
public class xxx {
    @Override
    public boolean applyLoadBalancerRules(Network network, List<LoadBalancingRule> loadBalancingRules) throws ResourceUnavailableException {
        // Find the external load balancer in this zone
        long zoneId = network.getDataCenterId();
        DataCenterVO zone = _dcDao.findById(zoneId);

        if (loadBalancingRules == null || loadBalancingRules.isEmpty()) {
            return true;
        }

        HostVO externalLoadBalancer = null;

        if(isNccServiceProvider(network)) {
            externalLoadBalancer  = getNetScalerControlCenterForNetwork(network);
        } else {
            ExternalLoadBalancerDeviceVO lbDeviceVO = getExternalLoadBalancerForNetwork(network);
            if (lbDeviceVO == null) {
                s_logger.warn("There is no external load balancer device assigned to this network either network is not implement are already shutdown so just returning");
                return true;
            } else {
                externalLoadBalancer = _hostDao.findById(lbDeviceVO.getHostId());
            }
        }



        boolean externalLoadBalancerIsInline = _networkMgr.isNetworkInlineMode(network);

        if (network.getState() == Network.State.Allocated) {
            s_logger.debug("External load balancer was asked to apply LB rules for network with ID " + network.getId() +
                "; this network is not implemented. Skipping backend commands.");
            return true;
        }

        List<LoadBalancerTO> loadBalancersToApply = new ArrayList<LoadBalancerTO>();
        List<MappingState> mappingStates = new ArrayList<MappingState>();
        for (int i = 0; i < loadBalancingRules.size(); i++) {
            LoadBalancingRule rule = loadBalancingRules.get(i);

            boolean revoked = (rule.getState().equals(FirewallRule.State.Revoke));
            String protocol = rule.getProtocol();
            String algorithm = rule.getAlgorithm();
            String uuid = rule.getUuid();
            String srcIp = rule.getSourceIp().addr();
            String srcIpVlan = null;
            String srcIpGateway = null;
            String srcIpNetmask = null;
            Long vlanid =  _networkModel.getPublicIpAddress(rule.getSourceIp().addr(), network.getDataCenterId()).getVlanId();
            if(vlanid != null ) {
              VlanVO publicVlan =   _vlanDao.findById(vlanid);
              srcIpVlan =  publicVlan.getVlanTag();
              srcIpGateway = publicVlan.getVlanGateway();
              srcIpNetmask = publicVlan.getVlanNetmask();
            }
            int srcPort = rule.getSourcePortStart();
            List<LbDestination> destinations = rule.getDestinations();

            if (externalLoadBalancerIsInline) {
                long ipId = _networkModel.getPublicIpAddress(rule.getSourceIp().addr(), network.getDataCenterId()).getId();
                MappingNic nic = getLoadBalancingIpNic(zone, network, ipId, revoked, null);
                mappingStates.add(nic.getState());
                Nic loadBalancingIpNic = nic.getNic();
                if (loadBalancingIpNic == null) {
                    continue;
                }

                // Change the source IP address for the load balancing rule to be the load balancing IP address
                srcIp = loadBalancingIpNic.getIPv4Address();
            }

            if ((destinations != null && !destinations.isEmpty()) || rule.isAutoScaleConfig()) {
                boolean inline = _networkMgr.isNetworkInlineMode(network);
                LoadBalancerTO loadBalancer =
                    new LoadBalancerTO(uuid, srcIp, srcPort, protocol, algorithm, revoked, false, inline, destinations, rule.getStickinessPolicies(),
                        rule.getHealthCheckPolicies(), rule.getLbSslCert(), rule.getLbProtocol());
                loadBalancer.setNetworkId(network.getId());
                loadBalancer.setSrcIpVlan(srcIpVlan);
                loadBalancer.setSrcIpNetmask(srcIpNetmask);
                loadBalancer.setSrcIpGateway(srcIpGateway);
                if (rule.isAutoScaleConfig()) {
                    loadBalancer.setAutoScaleVmGroup(rule.getAutoScaleVmGroup());
                }
                loadBalancersToApply.add(loadBalancer);
            }
        }

        try {
            if (loadBalancersToApply.size() > 0) {
                int numLoadBalancersForCommand = loadBalancersToApply.size();
                LoadBalancerTO[] loadBalancersForCommand = loadBalancersToApply.toArray(new LoadBalancerTO[numLoadBalancersForCommand]);
                LoadBalancerConfigCommand cmd = new LoadBalancerConfigCommand(loadBalancersForCommand, null);
                long guestVlanTag = Integer.parseInt(BroadcastDomainType.getValue(network.getBroadcastUri()));
                cmd.setAccessDetail(NetworkElementCommand.GUEST_VLAN_TAG, String.valueOf(guestVlanTag));
                Answer answer = _agentMgr.easySend(externalLoadBalancer.getId(), cmd);
                if (answer == null || !answer.getResult()) {
                    String details = (answer != null) ? answer.getDetails() : "details unavailable";
                    String msg = "Unable to apply load balancer rules to the external load balancer appliance in zone " + zone.getName() + " due to: " + details + ".";
                    s_logger.error(msg);
                    throw new ResourceUnavailableException(msg, DataCenter.class, network.getDataCenterId());
                }
            }
        } catch (Exception ex) {
            if (externalLoadBalancerIsInline) {
                s_logger.error("Rollbacking static nat operation of inline mode load balancing due to error on applying LB rules!");
                String existedGuestIp = loadBalancersToApply.get(0).getSrcIp();
                // Rollback static NAT operation in current session
                for (int i = 0; i < loadBalancingRules.size(); i++) {
                    LoadBalancingRule rule = loadBalancingRules.get(i);
                    MappingState state = mappingStates.get(i);
                    boolean revoke;
                    if (state == MappingState.Create) {
                        revoke = true;
                    } else if (state == MappingState.Remove) {
                        revoke = false;
                    } else {
                        continue;
                    }
                    long sourceIpId = _networkModel.getPublicIpAddress(rule.getSourceIp().addr(), network.getDataCenterId()).getId();
                    getLoadBalancingIpNic(zone, network, sourceIpId, revoke, existedGuestIp);
                }
            }
            throw new ResourceUnavailableException(ex.getMessage(), DataCenter.class, network.getDataCenterId());
        }

        return true;
    }

};