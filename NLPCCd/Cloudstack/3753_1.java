//,temp,sample_7707.java,2,13,temp,sample_511.java,2,13
//,2
public class xxx {
public void execute() throws ResourceUnavailableException, InsufficientCapacityException, ServerApiException, ConcurrentOperationException, ResourceAllocationException {
try {
StorageNetworkIpRange result = _storageNetworkService.updateIpRange(this);
StorageNetworkIpRangeResponse response = _responseGenerator.createStorageNetworkIpRangeResponse(result);
response.setResponseName(getCommandName());
this.setResponseObject(response);
} catch (Exception e) {


log.info("update storage network ip range failed");
}
}

};