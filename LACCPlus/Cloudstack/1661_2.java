//,temp,CreatePortForwardingRuleCmd.java,341,364,temp,CreateRemoteAccessVpnCmd.java,145,160
//,3
public class xxx {
    @Override
    public void create() {
        try {
            RemoteAccessVpn vpn = _ravService.createRemoteAccessVpn(publicIpId, ipRange, getOpenFirewall(), isDisplay());
            if (vpn != null) {
                setEntityId(vpn.getId());
                setEntityUuid(vpn.getUuid());
            } else {
                throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, "Failed to create remote access vpn");
            }
        } catch (NetworkRuleConflictException e) {
            s_logger.info("Network rule conflict: " + e.getMessage());
            s_logger.trace("Network Rule Conflict: ", e);
            throw new ServerApiException(ApiErrorCode.NETWORK_RULE_CONFLICT_ERROR, e.getMessage());
        }
    }

};