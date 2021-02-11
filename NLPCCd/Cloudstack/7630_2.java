//,temp,sample_5366.java,2,16,temp,sample_5367.java,2,16
//,2
public class xxx {
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


log.info("network rule conflict");
}
}

};