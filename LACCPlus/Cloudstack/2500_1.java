//,temp,NiciraNvpElement.java,887,920,temp,NiciraNvpElement.java,411,451
//,3
public class xxx {
    @Override
    public boolean applyPFRules(Network network, List<PortForwardingRule> rules) throws ResourceUnavailableException {
        if (!canHandle(network, Service.PortForwarding)) {
            return false;
        }

        List<NiciraNvpDeviceVO> devices = niciraNvpDao.listByPhysicalNetwork(network.getPhysicalNetworkId());
        if (devices.isEmpty()) {
            s_logger.error("No NiciraNvp Controller on physical network " + network.getPhysicalNetworkId());
            return false;
        }
        NiciraNvpDeviceVO niciraNvpDevice = devices.get(0);
        HostVO niciraNvpHost = hostDao.findById(niciraNvpDevice.getHostId());

        NiciraNvpRouterMappingVO routermapping = niciraNvpRouterMappingDao.findByNetworkId(network.getId());
        if (routermapping == null) {
            s_logger.error("No logical router uuid found for network " + network.getDisplayText());
            return false;
        }

        List<PortForwardingRuleTO> portForwardingRules = new ArrayList<PortForwardingRuleTO>();
        for (PortForwardingRule rule : rules) {
            IpAddress sourceIp = networkModel.getIp(rule.getSourceIpAddressId());
            Vlan vlan = vlanDao.findById(sourceIp.getVlanId());
            PortForwardingRuleTO ruleTO = new PortForwardingRuleTO(rule, vlan.getVlanTag(), sourceIp.getAddress().addr());
            portForwardingRules.add(ruleTO);
        }

        ConfigurePortForwardingRulesOnLogicalRouterCommand cmd =
                new ConfigurePortForwardingRulesOnLogicalRouterCommand(routermapping.getLogicalRouterUuid(), portForwardingRules);
        ConfigurePortForwardingRulesOnLogicalRouterAnswer answer = (ConfigurePortForwardingRulesOnLogicalRouterAnswer)agentMgr.easySend(niciraNvpHost.getId(), cmd);

        return answer.getResult();
    }

};