//,temp,NiciraNvpElement.java,887,920,temp,NiciraNvpElement.java,847,882
//,3
public class xxx {
    @Override
    public boolean applyStaticNats(Network network, List<? extends StaticNat> rules) throws ResourceUnavailableException {
        if (!canHandle(network, Service.StaticNat)) {
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

        List<StaticNatRuleTO> staticNatRules = new ArrayList<StaticNatRuleTO>();
        for (StaticNat rule : rules) {
            IpAddress sourceIp = networkModel.getIp(rule.getSourceIpAddressId());
            // Force the nat rule into the StaticNatRuleTO, no use making a new TO object
            // we only need the source and destination ip. Unfortunately no mention if a rule
            // is new.
            StaticNatRuleTO ruleTO =
                    new StaticNatRuleTO(1, sourceIp.getAddress().addr(), MIN_PORT, MAX_PORT, rule.getDestIpAddress(), MIN_PORT, MAX_PORT, "any", rule.isForRevoke(), false);
            staticNatRules.add(ruleTO);
        }

        ConfigureStaticNatRulesOnLogicalRouterCommand cmd = new ConfigureStaticNatRulesOnLogicalRouterCommand(routermapping.getLogicalRouterUuid(), staticNatRules);
        ConfigureStaticNatRulesOnLogicalRouterAnswer answer = (ConfigureStaticNatRulesOnLogicalRouterAnswer)agentMgr.easySend(niciraNvpHost.getId(), cmd);

        return answer.getResult();
    }

};