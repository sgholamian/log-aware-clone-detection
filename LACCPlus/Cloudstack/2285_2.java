//,temp,AddBaremetalPxeCmd.java,80,92,temp,CreateVpnConnectionCmd.java,150,165
//,3
public class xxx {
    @Override
    public void execute() {
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
            s_logger.warn("Exception: ", ex);
            throw new ServerApiException(ApiErrorCode.RESOURCE_UNAVAILABLE_ERROR, ex.getMessage());
        }
    }

};