//,temp,ExtractIsoCmd.java,129,146,temp,UpdateCloudToUseObjectStoreCmd.java,125,142
//,3
public class xxx {
    @Override
    public void execute() {
        try {
            CallContext.current().setEventDetails(getEventDescription());
            String uploadUrl = _templateService.extract(this);
            if (uploadUrl != null) {
                ExtractResponse response = _responseGenerator.createExtractResponse(id, zoneId, getEntityOwnerId(), mode, uploadUrl);
                response.setResponseName(getCommandName());
                response.setObjectName("iso");
                this.setResponseObject(response);
            } else {
                throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, "Failed to extract ISO");
            }
        } catch (InternalErrorException ex) {
            s_logger.warn("Exception: ", ex);
            throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, ex.getMessage());
        }
    }

};