//,temp,BlobOperations.java,366,376,temp,BlobOperations.java,274,284
//,3
public class xxx {
    public BlobOperationResponse resizePageBlob(final Exchange exchange) {
        LOG.trace("Resizing a page blob [{}] from exchange [{}]...", configurationProxy.getBlobName(exchange), exchange);

        final Long pageSize = getPageBlobSize(exchange);
        final BlobCommonRequestOptions requestOptions = getCommonRequestOptions(exchange);

        final Response<PageBlobItem> response
                = client.resizePageBlob(pageSize, requestOptions.getBlobRequestConditions(), requestOptions.getTimeout());

        return buildResponse(response, true);
    }

};