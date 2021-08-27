//,temp,BlobOperations.java,366,376,temp,BlobOperations.java,274,284
//,3
public class xxx {
    public BlobOperationResponse getBlobBlockList(final Exchange exchange) {
        LOG.trace("Getting the blob block list [{}] from exchange [{}]...", configurationProxy.getBlobName(exchange), exchange);

        final BlockListType blockListType = configurationProxy.getBlockListType(exchange);
        final BlobCommonRequestOptions commonRequestOptions = getCommonRequestOptions(exchange);

        final Response<BlockList> response
                = client.listBlobBlocks(blockListType, commonRequestOptions.leaseId(), commonRequestOptions.getTimeout());

        return buildResponse(response, false);
    }

};