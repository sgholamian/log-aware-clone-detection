//,temp,CiscoVnmcElement.java,754,807,temp,CiscoVnmcElement.java,641,697
//,3
public class xxx {
    @Override
    public boolean applyFWRules(Network network, List<? extends FirewallRule> rules) throws ResourceUnavailableException {

        if (!_networkModel.isProviderSupportServiceInNetwork(network.getId(), Service.Firewall, Provider.CiscoVnmc)) {
            s_logger.error("Firewall service is not provided by Cisco Vnmc device on network " + network.getName());
            return false;
        }

        // Find VNMC host for physical network
        List<CiscoVnmcControllerVO> devices = _ciscoVnmcDao.listByPhysicalNetwork(network.getPhysicalNetworkId());
        if (devices.isEmpty()) {
            s_logger.error("No Cisco Vnmc device on network " + network.getName());
            return true;
        }

        // Find if ASA 1000v is associated with network
        NetworkAsa1000vMapVO asaForNetwork = _networkAsa1000vMapDao.findByNetworkId(network.getId());
        if (asaForNetwork == null) {
            s_logger.debug("Cisco ASA 1000v device is not associated with network " + network.getName());
            return true;
        }

        if (network.getState() == Network.State.Allocated) {
            s_logger.debug("External firewall was asked to apply firewall rules for network with ID " + network.getId() +
                "; this network is not implemented. Skipping backend commands.");
            return true;
        }

        CiscoVnmcControllerVO ciscoVnmcDevice = devices.get(0);
        HostVO ciscoVnmcHost = _hostDao.findById(ciscoVnmcDevice.getHostId());

        List<FirewallRuleTO> rulesTO = new ArrayList<FirewallRuleTO>();
        for (FirewallRule rule : rules) {
            String address = "0.0.0.0";
            if (rule.getTrafficType() == TrafficType.Ingress) {
                IpAddress sourceIp = _networkModel.getIp(rule.getSourceIpAddressId());
                address = sourceIp.getAddress().addr();
            }
            FirewallRuleTO ruleTO = new FirewallRuleTO(rule, null, address, rule.getPurpose(), rule.getTrafficType());
            rulesTO.add(ruleTO);
        }

        if (!rulesTO.isEmpty()) {
            SetFirewallRulesCommand cmd = new SetFirewallRulesCommand(rulesTO);
            cmd.setContextParam(NetworkElementCommand.GUEST_VLAN_TAG, BroadcastDomainType.getValue(network.getBroadcastUri()));
            cmd.setContextParam(NetworkElementCommand.GUEST_NETWORK_CIDR, network.getCidr());
            Answer answer = _agentMgr.easySend(ciscoVnmcHost.getId(), cmd);
            if (answer == null || !answer.getResult()) {
                String details = (answer != null) ? answer.getDetails() : "details unavailable";
                String msg = "Unable to apply firewall rules to Cisco ASA 1000v appliance due to: " + details + ".";
                s_logger.error(msg);
                throw new ResourceUnavailableException(msg, DataCenter.class, network.getDataCenterId());
            }
        }

        return true;
    }

};