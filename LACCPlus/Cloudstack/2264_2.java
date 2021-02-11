//,temp,DeleteAutoScaleVmProfileCmd.java,93,104,temp,DeleteCounterCmd.java,53,70
//,3
public class xxx {
    @Override
    public void execute() {
        boolean result = false;
        try {
            result = _autoScaleService.deleteCounter(getId());
        } catch (ResourceInUseException ex) {
            s_logger.warn("Exception: ", ex);
            throw new ServerApiException(ApiErrorCode.RESOURCE_IN_USE_ERROR, ex.getMessage());
        }

        if (result) {
            SuccessResponse response = new SuccessResponse(getCommandName());
            this.setResponseObject(response);
        } else {
            s_logger.warn("Failed to delete counter with Id: " + getId());
            throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, "Failed to delete counter.");
        }
    }

};