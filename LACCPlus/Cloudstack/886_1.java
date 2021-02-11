//,temp,CreateStaticRouteCmd.java,72,83,temp,CreateIpForwardingRuleCmd.java,145,162
//,3
public class xxx {
    @Override
    public void create() throws ResourceAllocationException {
        try {
            StaticRoute result = _vpcService.createStaticRoute(getGatewayId(), getCidr());
            setEntityId(result.getId());
            setEntityUuid(result.getUuid());
        } catch (NetworkRuleConflictException ex) {
            s_logger.info("Network rule conflict: " + ex.getMessage());
            s_logger.trace("Network rule conflict: ", ex);
            throw new ServerApiException(ApiErrorCode.NETWORK_RULE_CONFLICT_ERROR, ex.getMessage());
        }
    }

};