//,temp,ListBaremetalPxeServersCmd.java,69,83,temp,GetUploadParamsForVolumeCmd.java,57,68
//,3
public class xxx {
    @Override
    public void execute() throws ResourceUnavailableException, InsufficientCapacityException, ServerApiException, ConcurrentOperationException,
        ResourceAllocationException, NetworkRuleConflictException {
        try {
            ListResponse<BaremetalPxeResponse> response = new ListResponse<BaremetalPxeResponse>();
            List<BaremetalPxeResponse> pxeResponses = _pxeMgr.listPxeServers(this);
            response.setResponses(pxeResponses);
            response.setResponseName(getCommandName());
            response.setObjectName("baremetalpxeservers");
            this.setResponseObject(response);
        } catch (Exception e) {
            s_logger.debug("Exception happened while executing ListPingPxeServersCmd", e);
            throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, e.getMessage());
        }
    }

};