//,temp,CiscoVnmcElement.java,754,807,temp,CiscoVnmcElement.java,699,752
//,3
public class xxx {
    @Override
    public boolean applyStaticNats(Network network, List<? extends StaticNat> rules) throws ResourceUnavailableException {
        if (!_networkModel.isProviderSupportServiceInNetwork(network.getId(), Service.StaticNat, Provider.CiscoVnmc)) {
            s_logger.error("Static NAT service is not provided by Cisco Vnmc device on network " + network.getName());
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
            s_logger.debug("External firewall was asked to apply static NAT rules for network with ID " + network.getId() +
                "; this network is not implemented. Skipping backend commands.");
            return true;
        }

        CiscoVnmcControllerVO ciscoVnmcDevice = devices.get(0);
        HostVO ciscoVnmcHost = _hostDao.findById(ciscoVnmcDevice.getHostId());

        List<StaticNatRuleTO> rulesTO = new ArrayList<StaticNatRuleTO>();
        for (StaticNat rule : rules) {
            IpAddress sourceIp = _networkModel.getIp(rule.getSourceIpAddressId());
            StaticNatRuleTO ruleTO =
                new StaticNatRuleTO(rule.getSourceIpAddressId(), sourceIp.getAddress().addr(), null, null, rule.getDestIpAddress(), null, null, null, rule.isForRevoke(),
                    false);
            rulesTO.add(ruleTO);
        }

        if (!rulesTO.isEmpty()) {
            SetStaticNatRulesCommand cmd = new SetStaticNatRulesCommand(rulesTO, null);
            cmd.setContextParam(NetworkElementCommand.GUEST_VLAN_TAG, BroadcastDomainType.getValue(network.getBroadcastUri()));
            cmd.setContextParam(NetworkElementCommand.GUEST_NETWORK_CIDR, network.getCidr());
            Answer answer = _agentMgr.easySend(ciscoVnmcHost.getId(), cmd);
            if (answer == null || !answer.getResult()) {
                String details = (answer != null) ? answer.getDetails() : "details unavailable";
                String msg = "Unable to apply static NAT rules to Cisco ASA 1000v appliance due to: " + details + ".";
                s_logger.error(msg);
                throw new ResourceUnavailableException(msg, DataCenter.class, network.getDataCenterId());
            }
        }

        return true;
    }

};