//,temp,DeleteAutoScaleVmProfileCmd.java,93,104,temp,DeleteCounterCmd.java,53,70
//,3
public class xxx {
    @Override
    public void execute() {
        CallContext.current().setEventDetails("AutoScale VM Profile Id: " + getId());
        boolean result = _autoScaleService.deleteAutoScaleVmProfile(id);
        if (result) {
            SuccessResponse response = new SuccessResponse(getCommandName());
            setResponseObject(response);
        } else {
            s_logger.warn("Failed to delete autoscale vm profile " + getId());
            throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, "Failed to delete autoscale vm profile");
        }
    }

};