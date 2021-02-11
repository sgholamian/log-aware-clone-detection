//,temp,sample_3010.java,2,17,temp,sample_6722.java,2,17
//,3
public class xxx {
public void dummy_method(){
try {
Site2SiteVpnConnection result = _s2sVpnService.startVpnConnection(getEntityId());
if (result != null) {
Site2SiteVpnConnectionResponse response = _responseGenerator.createSite2SiteVpnConnectionResponse(result);
response.setResponseName(getCommandName());
setResponseObject(response);
} else {
throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, "Failed to create site to site vpn connection");
}
} catch (ResourceUnavailableException ex) {


log.info("exception");
}
}

};