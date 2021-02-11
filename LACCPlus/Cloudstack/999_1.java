//,temp,GetUploadParamsForIsoCmd.java,126,137,temp,UpdateStorageNetworkIpRangeCmd.java,102,115
//,3
public class xxx {
    @Override
    public void execute() throws ResourceUnavailableException, InsufficientCapacityException, ServerApiException, ConcurrentOperationException, ResourceAllocationException, NetworkRuleConflictException {
        validateRequest();
        try {
            GetUploadParamsResponse response = _templateService.registerIsoForPostUpload(this);
            response.setResponseName(getCommandName());
            setResponseObject(response);
        } catch (ResourceAllocationException | MalformedURLException e) {
            s_logger.error("Exception while registering template", e);
            throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, "Exception while registering ISO: " + e.getMessage());
        }
    }

};