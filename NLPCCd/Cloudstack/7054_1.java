//,temp,sample_3010.java,2,17,temp,sample_6722.java,2,17
//,3
public class xxx {
public void dummy_method(){
try {
Site2SiteVpnConnection result = _s2sVpnService.resetVpnConnection(this);
if (result != null) {
Site2SiteVpnConnectionResponse response = _responseGenerator.createSite2SiteVpnConnectionResponse(result);
response.setResponseName(getCommandName());
setResponseObject(response);
} else {
throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, "Failed to reset site to site VPN connection");
}
} catch (ResourceUnavailableException ex) {


log.info("exception");
}
}

};