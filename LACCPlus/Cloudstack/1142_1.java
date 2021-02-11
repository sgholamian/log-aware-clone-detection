//,temp,ExternalFirewallDeviceManagerImpl.java,814,842,temp,ExternalFirewallDeviceManagerImpl.java,611,639
//,3
public class xxx {
    @Override
    public boolean applyPortForwardingRules(Network network, List<? extends PortForwardingRule> rules) throws ResourceUnavailableException {
        // Find the external firewall in this zone
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

        List<PortForwardingRuleTO> pfRules = new ArrayList<PortForwardingRuleTO>();

        for (PortForwardingRule rule : rules) {
            IpAddress sourceIp = _networkModel.getIp(rule.getSourceIpAddressId());
            Vlan vlan = _vlanDao.findById(sourceIp.getVlanId());

            PortForwardingRuleTO ruleTO = new PortForwardingRuleTO(rule, vlan.getVlanTag(), sourceIp.getAddress().addr());
            pfRules.add(ruleTO);
        }

        sendPortForwardingRules(pfRules, zone, externalFirewall.getId());
        return true;
    }

};