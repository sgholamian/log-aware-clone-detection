//,temp,CreateLBHealthCheckPolicyCmd.java,183,193,temp,CreateLBStickinessPolicyCmd.java,160,170
//,2
public class xxx {
    @Override
    public void create() {
        try {
            StickinessPolicy result = _lbService.createLBStickinessPolicy(this);
            this.setEntityId(result.getId());
            this.setEntityUuid(result.getUuid());
        } catch (NetworkRuleConflictException e) {
            s_logger.warn("Exception: ", e);
            throw new ServerApiException(ApiErrorCode.NETWORK_RULE_CONFLICT_ERROR, e.getMessage());
        }
    }

};