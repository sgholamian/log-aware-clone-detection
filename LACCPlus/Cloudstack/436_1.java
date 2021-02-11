//,temp,AddSwiftCmd.java,92,114,temp,AddSecondaryStorageCmd.java,76,93
//,3
public class xxx {
    @Override
    public void execute() {
        Map<String, String> dm = new HashMap<String, String>();
        dm.put(ApiConstants.ACCOUNT, getAccount());
        dm.put(ApiConstants.USERNAME, getUsername());
        dm.put(ApiConstants.KEY, getKey());

        try{
            ImageStore result = _storageService.discoverImageStore(null, getUrl(), "Swift", null, dm);
            ImageStoreResponse storeResponse = null;
            if (result != null) {
                storeResponse = _responseGenerator.createImageStoreResponse(result);
                storeResponse.setResponseName(getCommandName());
                storeResponse.setObjectName("secondarystorage");
                setResponseObject(storeResponse);
            } else {
                throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, "Failed to add Swift secondary storage");
            }
        } catch (DiscoveryException ex) {
            s_logger.warn("Exception: ", ex);
            throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, ex.getMessage());
        }
    }

};