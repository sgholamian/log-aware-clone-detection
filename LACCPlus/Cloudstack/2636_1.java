//,temp,CreateApplicationLoadBalancerCmd.java,219,238,temp,CreateLoadBalancerRuleCmd.java,307,329
//,3
public class xxx {
    @Override
    public void create() {
        try {

            ApplicationLoadBalancerRule result =
                _appLbService.createApplicationLoadBalancer(getName(), getDescription(), getScheme(), getSourceIpNetworkId(), getSourceIp(), getSourcePort(),
                    getInstancePort(), getAlgorithm(), getNetworkId(), getEntityOwnerId(), getDisplay());
            this.setEntityId(result.getId());
            this.setEntityUuid(result.getUuid());
        } catch (NetworkRuleConflictException e) {
            s_logger.warn("Exception: ", e);
            throw new ServerApiException(ApiErrorCode.NETWORK_RULE_CONFLICT_ERROR, e.getMessage());
        } catch (InsufficientAddressCapacityException e) {
            s_logger.warn("Exception: ", e);
            throw new ServerApiException(ApiErrorCode.INSUFFICIENT_CAPACITY_ERROR, e.getMessage());
        } catch (InsufficientVirtualNetworkCapacityException e) {
            s_logger.warn("Exception: ", e);
            throw new ServerApiException(ApiErrorCode.INSUFFICIENT_CAPACITY_ERROR, e.getMessage());
        }
    }

};