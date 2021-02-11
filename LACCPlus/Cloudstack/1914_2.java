//,temp,ScaleSystemVMCmd.java,102,134,temp,CreateStoragePoolCmd.java,160,183
//,3
public class xxx {
    @Override
    public void execute() {
        try {
            StoragePool result = _storageService.createPool(this);
            if (result != null) {
                StoragePoolResponse response = _responseGenerator.createStoragePoolResponse(result);
                response.setResponseName(getCommandName());
                this.setResponseObject(response);
            } else {
                throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, "Failed to add storage pool");
            }
        } catch (ResourceUnavailableException ex1) {
            s_logger.warn("Exception: ", ex1);
            throw new ServerApiException(ApiErrorCode.RESOURCE_UNAVAILABLE_ERROR, ex1.getMessage());
        } catch (ResourceInUseException ex2) {
            s_logger.warn("Exception: ", ex2);
            throw new ServerApiException(ApiErrorCode.RESOURCE_IN_USE_ERROR, ex2.getMessage());
        } catch (UnknownHostException ex3) {
            s_logger.warn("Exception: ", ex3);
            throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, ex3.getMessage());
        } catch (Exception ex4) {
            throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, ex4.getMessage());
        }
    }

};