//,temp,CreateRemoteAccessVpnCmd.java,162,177,temp,CreateStorageNetworkIpRangeCmd.java,114,126
//,3
public class xxx {
    @Override
    public void execute() {
        try {
            RemoteAccessVpn result = _ravService.startRemoteAccessVpn(publicIpId, getOpenFirewall());
            if (result != null) {
                RemoteAccessVpnResponse response = _responseGenerator.createRemoteAccessVpnResponse(result);
                response.setResponseName(getCommandName());
                setResponseObject(response);
            } else {
                throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, "Failed to create remote access vpn");
            }
        } catch (ResourceUnavailableException ex) {
            s_logger.warn("Exception: ", ex);
            throw new ServerApiException(ApiErrorCode.RESOURCE_UNAVAILABLE_ERROR, ex.getMessage());
        }
    }

};