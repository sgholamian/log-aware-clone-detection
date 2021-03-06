//,temp,CreateLBHealthCheckPolicyCmd.java,183,193,temp,CreateGlobalLoadBalancerRuleCmd.java,158,171
//,3
public class xxx {
    @Override
    public void create() {
        try {
            HealthCheckPolicy result = _lbService.createLBHealthCheckPolicy(this);
            this.setEntityId(result.getId());
            this.setEntityUuid(result.getUuid());
        } catch (InvalidParameterValueException e) {
            s_logger.warn("Exception: ", e);
            throw new ServerApiException(ApiErrorCode.MALFORMED_PARAMETER_ERROR, e.getMessage());
        }
    }

};