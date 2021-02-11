//,temp,DeleteAutoScalePolicyCmd.java,93,105,temp,DeleteAutoScaleVmProfileCmd.java,93,104
//,2
public class xxx {
    @Override
    public void execute() {
        CallContext.current().setEventDetails("AutoScale Policy Id: " + getId());
        boolean result = _autoScaleService.deleteAutoScalePolicy(id);

        if (result) {
            SuccessResponse response = new SuccessResponse(getCommandName());
            setResponseObject(response);
        } else {
            s_logger.warn("Failed to delete autoscale policy " + getId());
            throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, "Failed to delete AutoScale Policy");
        }
    }

};