//,temp,BlobOperations.java,323,335,temp,BlobOperations.java,286,296
//,3
public class xxx {
    public BlobOperationResponse createPageBlob(final Exchange exchange) {
        LOG.trace("Creating a page blob [{}] from exchange [{}]...", configurationProxy.getBlobName(exchange), exchange);

        final Long pageSize = getPageBlobSize(exchange);
        final BlobCommonRequestOptions requestOptions = getCommonRequestOptions(exchange);
        final Long sequenceNumber = configurationProxy.getBlobSequenceNumber(exchange);

        final Response<PageBlobItem> response
                = client.createPageBlob(pageSize, sequenceNumber, requestOptions.getBlobHttpHeaders(),
                        requestOptions.getMetadata(), requestOptions.getBlobRequestConditions(), requestOptions.getTimeout());

        return buildResponse(response, true);
    }

};