//,temp,DeleteVpnConnectionCmd.java,84,98,temp,DeleteSecurityGroupCmd.java,122,136
//,2
public class xxx {
    @Override
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
            s_logger.warn("Exception: ", ex);
            throw new ServerApiException(ApiErrorCode.RESOURCE_UNAVAILABLE_ERROR, ex.getMessage());
        }
    }

};