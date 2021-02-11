//,temp,DeleteBaremetalRctCmd.java,60,70,temp,ResetVpnConnectionCmd.java,102,117
//,3
public class xxx {
    @Override
    public void execute() throws ResourceUnavailableException, InsufficientCapacityException, ServerApiException, ConcurrentOperationException, ResourceAllocationException, NetworkRuleConflictException {
        try {
            vlanMgr.deleteRct(this);
            SuccessResponse response = new SuccessResponse(getCommandName());
            setResponseObject(response);
        } catch (Exception e) {
            s_logger.warn(String.format("unable to delete baremetal RCT[%s]", getId()), e);
            throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, e.getMessage(), e);
        }
    }

};