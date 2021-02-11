//,temp,CreateLBStickinessPolicyCmd.java,160,170,temp,CreatePortableIpRangeCmd.java,124,138
//,3
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