//,temp,sample_7348.java,2,16,temp,sample_5777.java,2,16
//,3
public class xxx {
public void execute() {
try {
boolean result = _s2sVpnService.deleteVpnConnection(this);
if (result) {
SuccessResponse response = new SuccessResponse(getCommandName());
setResponseObject(response);
} else {
throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, "Failed to delete site to site VPN connection");
}
} catch (ResourceUnavailableException ex) {


log.info("exception");
}
}

};