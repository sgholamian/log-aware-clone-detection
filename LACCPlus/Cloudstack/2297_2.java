//,temp,ResetVpnConnectionCmd.java,102,117,temp,CreateSecondaryStagingStoreCmd.java,108,125
//,3
public class xxx {
    @Override
    public void execute() {
        try {
            ImageStore result = _storageService.createSecondaryStagingStore(this);
            ImageStoreResponse storeResponse = null;
            if (result != null) {
                storeResponse = _responseGenerator.createImageStoreResponse(result);
                storeResponse.setResponseName(getCommandName());
                storeResponse.setObjectName("secondarystorage");
                this.setResponseObject(storeResponse);
            } else {
                throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, "Failed to add secondary storage");
            }
        } catch (Exception ex) {
            s_logger.warn("Exception: ", ex);
            throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, ex.getMessage());
        }
    }

};