//,temp,CreateApplicationLoadBalancerCmd.java,219,238,temp,CreateLoadBalancerRuleCmd.java,307,329
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
            LoadBalancer result =
                _lbService.createPublicLoadBalancerRule(getXid(), getName(), getDescription(), getSourcePortStart(), getSourcePortEnd(), getDefaultPortStart(),
                    getDefaultPortEnd(), getSourceIpAddressId(), getProtocol(), getAlgorithm(), getNetworkId(), getEntityOwnerId(), getOpenFirewall(), getLbProtocol(), isDisplay());
            this.setEntityId(result.getId());
            this.setEntityUuid(result.getUuid());
        } catch (NetworkRuleConflictException e) {
            s_logger.warn("Exception: ", e);
            throw new ServerApiException(ApiErrorCode.NETWORK_RULE_CONFLICT_ERROR, e.getMessage());
        } catch (InsufficientAddressCapacityException e) {
            s_logger.warn("Exception: ", e);
            throw new ServerApiException(ApiErrorCode.INSUFFICIENT_CAPACITY_ERROR, e.getMessage());
        } catch (InvalidParameterValueException e) {
            throw new ServerApiException(ApiErrorCode.PARAM_ERROR, e.getMessage());
        }
    }

};