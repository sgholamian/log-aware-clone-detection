//,temp,ExternalFirewallDeviceManagerImpl.java,654,665,temp,ExternalFirewallDeviceManagerImpl.java,641,652
//,3
public class xxx {
    protected void sendFirewallRules(List<FirewallRuleTO> firewallRules, DataCenter zone, long externalFirewallId) throws ResourceUnavailableException {
        if (!firewallRules.isEmpty()) {
            SetFirewallRulesCommand cmd = new SetFirewallRulesCommand(firewallRules);
            Answer answer = _agentMgr.easySend(externalFirewallId, cmd);
            if (answer == null || !answer.getResult()) {
                String details = (answer != null) ? answer.getDetails() : "details unavailable";
                String msg = "External firewall was unable to apply static nat rules to the SRX appliance in zone " + zone.getName() + " due to: " + details + ".";
                s_logger.error(msg);
                throw new ResourceUnavailableException(msg, DataCenter.class, zone.getId());
            }
        }
    }

};