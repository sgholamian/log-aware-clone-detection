//,temp,GetUploadParamsForTemplateCmd.java,151,162,temp,GetUploadParamsForIsoCmd.java,126,137
//,3
public class xxx {
    @Override
    public void execute() throws ServerApiException {
        validateRequest();
        try {
            GetUploadParamsResponse response = _templateService.registerTemplateForPostUpload(this);
            response.setResponseName(getCommandName());
            setResponseObject(response);
        } catch (ResourceAllocationException | MalformedURLException e) {
            s_logger.error("exception while registering template", e);
            throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, "exception while registering template: " + e.getMessage());
        }
    }

};