//,temp,CreatePortForwardingRuleCmd.java,341,364,temp,CreateRemoteAccessVpnCmd.java,145,160
//,3
public class xxx {
    @Override
    public void create() {
        // cidr list parameter is deprecated
        if (cidrlist != null) {
            throw new InvalidParameterValueException(
                "Parameter cidrList is deprecated; if you need to open firewall rule for the specific cidr, please refer to createFirewallRule command");
        }

        Ip privateIp = getVmSecondaryIp();
        if (privateIp != null) {
            if (!NetUtils.isValidIp4(privateIp.toString())) {
                throw new InvalidParameterValueException("Invalid vm ip address");
            }
        }

        try {
            PortForwardingRule result = _rulesService.createPortForwardingRule(this, virtualMachineId, privateIp, getOpenFirewall(), isDisplay());
            setEntityId(result.getId());
            setEntityUuid(result.getUuid());
        } catch (NetworkRuleConflictException ex) {
            s_logger.trace("Network Rule Conflict: ", ex);
            throw new ServerApiException(ApiErrorCode.NETWORK_RULE_CONFLICT_ERROR, ex.getMessage(), ex);
        }
    }

};