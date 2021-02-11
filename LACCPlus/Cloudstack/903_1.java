//,temp,DeleteConditionCmd.java,57,73,temp,DeleteAutoScalePolicyCmd.java,93,105
//,3
public class xxx {
    @Override
    public void execute() {
        boolean result = false;
        try {
            result = _autoScaleService.deleteCondition(getId());
        } catch (ResourceInUseException ex) {
            s_logger.warn("Exception: ", ex);
            throw new ServerApiException(ApiErrorCode.RESOURCE_IN_USE_ERROR, ex.getMessage());
        }
        if (result) {
            SuccessResponse response = new SuccessResponse(getCommandName());
            setResponseObject(response);
        } else {
            s_logger.warn("Failed to delete condition " + getId());
            throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, "Failed to delete condition.");
        }
    }

};