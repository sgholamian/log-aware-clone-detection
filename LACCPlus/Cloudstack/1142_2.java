//,temp,ExternalFirewallDeviceManagerImpl.java,814,842,temp,ExternalFirewallDeviceManagerImpl.java,611,639
//,3
public class xxx {
    public boolean applyStaticNatRules(Network network, List<? extends StaticNat> rules) throws ResourceUnavailableException {
        long zoneId = network.getDataCenterId();
        DataCenterVO zone = _dcDao.findById(zoneId);
        ExternalFirewallDeviceVO fwDeviceVO = getExternalFirewallForNetwork(network);
        HostVO externalFirewall = _hostDao.findById(fwDeviceVO.getHostId());

        assert (externalFirewall != null);

        if (network.getState() == Network.State.Allocated) {
            s_logger.debug("External firewall was asked to apply firewall rules for network with ID " + network.getId() +
                "; this network is not implemented. Skipping backend commands.");
            return true;
        }

        List<StaticNatRuleTO> staticNatRules = new ArrayList<StaticNatRuleTO>();

        for (StaticNat rule : rules) {
            IpAddress sourceIp = _networkModel.getIp(rule.getSourceIpAddressId());
            Vlan vlan = _vlanDao.findById(sourceIp.getVlanId());

            StaticNatRuleTO ruleTO =
                new StaticNatRuleTO(0, vlan.getVlanTag(), sourceIp.getAddress().addr(), -1, -1, rule.getDestIpAddress(), -1, -1, "any", rule.isForRevoke(), false);
            staticNatRules.add(ruleTO);
        }

        sendStaticNatRules(staticNatRules, zone, externalFirewall.getId());

        return true;
    }

};