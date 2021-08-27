//,temp,BlobOperations.java,323,335,temp,BlobOperations.java,286,296
//,3
public class xxx {
    public BlobOperationResponse createAppendBlob(final Exchange exchange) {
        LOG.trace("Creating an append blob [{}] from exchange [{}]...", configurationProxy.getBlobName(exchange), exchange);

        final BlobCommonRequestOptions commonRequestOptions = getCommonRequestOptions(exchange);

        final Response<AppendBlobItem> response
                = client.createAppendBlob(commonRequestOptions.getBlobHttpHeaders(), commonRequestOptions.getMetadata(),
                        commonRequestOptions.getBlobRequestConditions(), commonRequestOptions.getTimeout());

        return buildResponse(response, true);
    }

};