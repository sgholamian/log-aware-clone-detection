//,temp,sample_6720.java,2,16,temp,sample_6721.java,2,16
//,2
public class xxx {
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


log.info("network rule conflict");
}
}

};