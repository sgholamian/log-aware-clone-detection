//,temp,DeleteAutoScaleVmGroupCmd.java,93,105,temp,DeleteStorageNetworkIpRangeCmd.java,73,84
//,3
public class xxx {
    @Override
    public void execute() {
        CallContext.current().setEventDetails("AutoScale Vm Group Id: " + getId());
        boolean result = _autoScaleService.deleteAutoScaleVmGroup(id);

        if (result) {
            SuccessResponse response = new SuccessResponse(getCommandName());
            setResponseObject(response);
        } else {
            s_logger.warn("Failed to delete autoscale vm group " + getId());
            throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, "Failed to delete autoscale vm group");
        }
    }

};