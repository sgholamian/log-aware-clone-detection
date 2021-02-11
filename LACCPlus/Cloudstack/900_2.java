//,temp,CreateLBHealthCheckPolicyCmd.java,183,193,temp,CreateGlobalLoadBalancerRuleCmd.java,158,171
//,3
public class xxx {
    @Override
    public void create() {
        try {
            GlobalLoadBalancerRule gslbRule = _gslbService.createGlobalLoadBalancerRule(this);
            this.setEntityId(gslbRule.getId());
            this.setEntityUuid(gslbRule.getUuid());
            CallContext.current().setEventDetails("Rule Id: " + getEntityId());
        } catch (Exception ex) {
            s_logger.warn("Exception: ", ex);
            throw new ServerApiException(ApiErrorCode.PARAM_ERROR, ex.getMessage());
        } finally {

        }
    }

};