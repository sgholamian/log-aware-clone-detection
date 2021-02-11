//,temp,CreateAutoScaleVmGroupCmd.java,211,232,temp,CreateVpnConnectionCmd.java,150,165
//,3
public class xxx {
    @Override
    public void execute() {
        boolean success = false;
        AutoScaleVmGroup vmGroup = null;
        try {
            success = _autoScaleService.configureAutoScaleVmGroup(this);
            if (success) {
                vmGroup = _entityMgr.findById(AutoScaleVmGroup.class, getEntityId());
                AutoScaleVmGroupResponse responseObject = _responseGenerator.createAutoScaleVmGroupResponse(vmGroup);
                setResponseObject(responseObject);
                responseObject.setResponseName(getCommandName());
            }
        } catch (Exception ex) {
            // TODO what will happen if Resource Layer fails in a step inbetween
            s_logger.warn("Failed to create autoscale vm group", ex);
        } finally {
            if (!success || vmGroup == null) {
                _autoScaleService.deleteAutoScaleVmGroup(getEntityId());
                throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, "Failed to create Autoscale Vm Group");
            }
        }
    }

};