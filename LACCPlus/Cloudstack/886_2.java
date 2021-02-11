//,temp,CreateStaticRouteCmd.java,72,83,temp,CreateIpForwardingRuleCmd.java,145,162
//,3
public class xxx {
    @Override
    public void create() {

        //cidr list parameter is deprecated
        if (cidrlist != null) {
            throw new InvalidParameterValueException(
                "Parameter cidrList is deprecated; if you need to open firewall rule for the specific CIDR, please refer to createFirewallRule command");
        }

        try {
            StaticNatRule rule = _rulesService.createStaticNatRule(this, getOpenFirewall());
            setEntityId(rule.getId());
            setEntityUuid(rule.getUuid());
        } catch (NetworkRuleConflictException e) {
            s_logger.info("Unable to create static NAT rule due to ", e);
            throw new ServerApiException(ApiErrorCode.NETWORK_RULE_CONFLICT_ERROR, e.getMessage());
        }
    }

};