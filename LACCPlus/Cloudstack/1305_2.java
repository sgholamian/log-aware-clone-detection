//,temp,CiscoVnmcElement.java,754,807,temp,CiscoVnmcElement.java,699,752
//,3
public class xxx {
    @Override
    public boolean applyPFRules(Network network, List<PortForwardingRule> rules) throws ResourceUnavailableException {

        if (!_networkModel.isProviderSupportServiceInNetwork(network.getId(), Service.PortForwarding, Provider.CiscoVnmc)) {
            s_logger.error("Port forwarding service is not provided by Cisco Vnmc device on network " + network.getName());
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
            s_logger.debug("External firewall was asked to apply port forwarding rules for network with ID " + network.getId() +
                "; this network is not implemented. Skipping backend commands.");
            return true;
        }

        CiscoVnmcControllerVO ciscoVnmcDevice = devices.get(0);
        HostVO ciscoVnmcHost = _hostDao.findById(ciscoVnmcDevice.getHostId());

        List<PortForwardingRuleTO> rulesTO = new ArrayList<PortForwardingRuleTO>();
        for (PortForwardingRule rule : rules) {
            IpAddress sourceIp = _networkModel.getIp(rule.getSourceIpAddressId());
            Vlan vlan = _vlanDao.findById(sourceIp.getVlanId());
            PortForwardingRuleTO ruleTO = new PortForwardingRuleTO(rule, vlan.getVlanTag(), sourceIp.getAddress().addr());
            rulesTO.add(ruleTO);
        }

        if (!rulesTO.isEmpty()) {
            SetPortForwardingRulesCommand cmd = new SetPortForwardingRulesCommand(rulesTO);
            cmd.setContextParam(NetworkElementCommand.GUEST_VLAN_TAG, BroadcastDomainType.getValue(network.getBroadcastUri()));
            cmd.setContextParam(NetworkElementCommand.GUEST_NETWORK_CIDR, network.getCidr());
            Answer answer = _agentMgr.easySend(ciscoVnmcHost.getId(), cmd);
            if (answer == null || !answer.getResult()) {
                String details = (answer != null) ? answer.getDetails() : "details unavailable";
                String msg = "Unable to apply port forwarding rules to Cisco ASA 1000v appliance due to: " + details + ".";
                s_logger.error(msg);
                throw new ResourceUnavailableException(msg, DataCenter.class, network.getDataCenterId());
            }
        }

        return true;
    }

};