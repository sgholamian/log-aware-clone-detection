//,temp,EnableStaticNatCmd.java,130,145,temp,CreateVpnConnectionCmd.java,133,148
//,3
public class xxx {
    @Override
    public void create() {
        try {
            Site2SiteVpnConnection conn = _s2sVpnService.createVpnConnection(this);
            if (conn != null) {
                setEntityId(conn.getId());
                setEntityUuid(conn.getUuid());
            } else {
                throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, "Failed to create site to site vpn connection");
            }
        } catch (NetworkRuleConflictException e) {
            s_logger.info("Network rule conflict: " + e.getMessage());
            s_logger.trace("Network Rule Conflict: ", e);
            throw new ServerApiException(ApiErrorCode.NETWORK_RULE_CONFLICT_ERROR, e.getMessage());
        }
    }

};