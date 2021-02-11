//,temp,AddImageStoreCmd.java,132,149,temp,AddSwiftCmd.java,92,114
//,3
public class xxx {
    @Override
    public void execute(){
        try{
            ImageStore result = _storageService.discoverImageStore(getName(), getUrl(), getProviderName(), getZoneId(), getDetails());
            ImageStoreResponse storeResponse = null;
            if (result != null) {
                storeResponse = _responseGenerator.createImageStoreResponse(result);
                storeResponse.setResponseName(getCommandName());
                storeResponse.setObjectName("imagestore");
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