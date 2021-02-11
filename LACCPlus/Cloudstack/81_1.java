//,temp,ResetVpnConnectionCmd.java,102,117,temp,CreateRemoteAccessVpnCmd.java,162,177
//,3
public class xxx {
    @Override
    public void execute() {
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
            s_logger.warn("Exception: ", ex);
            throw new ServerApiException(ApiErrorCode.RESOURCE_UNAVAILABLE_ERROR, ex.getMessage());
        }
    }

};