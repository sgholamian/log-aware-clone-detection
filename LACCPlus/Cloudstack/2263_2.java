//,temp,DeleteAutoScaleVmProfileCmd.java,93,104,temp,DeleteStorageNetworkIpRangeCmd.java,73,84
//,3
public class xxx {
    @Override
    public void execute() throws ResourceUnavailableException, InsufficientCapacityException, ServerApiException, ConcurrentOperationException,
        ResourceAllocationException {
        try {
            _storageNetworkService.deleteIpRange(this);
            SuccessResponse response = new SuccessResponse(getCommandName());
            this.setResponseObject(response);
        } catch (Exception e) {
            s_logger.warn("Failed to delete storage network ip range " + getId(), e);
            throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, e.getMessage());
        }
    }

};