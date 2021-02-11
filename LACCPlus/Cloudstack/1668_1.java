//,temp,ListUcsBladeCmd.java,62,74,temp,ResetVpnConnectionCmd.java,102,117
//,3
public class xxx {
    @Override
    public void execute() throws ResourceUnavailableException, InsufficientCapacityException, ServerApiException, ConcurrentOperationException,
        ResourceAllocationException, NetworkRuleConflictException {
        try {
            ListResponse<UcsBladeResponse> response = mgr.listUcsBlades(this);
            response.setResponseName(getCommandName());
            response.setObjectName("ucsblade");
            this.setResponseObject(response);
        } catch (Exception e) {
            s_logger.warn(e.getMessage(), e);
            throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, e.getMessage());
        }
    }

};