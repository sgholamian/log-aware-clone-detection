//,temp,UpdateCloudToUseObjectStoreCmd.java,125,142,temp,AddSecondaryStorageCmd.java,76,93
//,3
public class xxx {
    @Override
    public void execute(){
        try{
            ImageStore result = _storageService.discoverImageStore(null, getUrl(), "NFS", getZoneId(), null);
            ImageStoreResponse storeResponse = null;
            if (result != null ) {
                    storeResponse = _responseGenerator.createImageStoreResponse(result);
                    storeResponse.setResponseName(getCommandName());
                    storeResponse.setObjectName("secondarystorage");
                    setResponseObject(storeResponse);
            } else {
                throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, "Failed to add secondary storage");
            }
        } catch (DiscoveryException ex) {
            s_logger.warn("Exception: ", ex);
            throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, ex.getMessage());
        }
    }

};