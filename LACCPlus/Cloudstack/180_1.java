//,temp,AddUcsManagerCmd.java,58,70,temp,ResetVpnConnectionCmd.java,102,117
//,3
public class xxx {
    @Override
    public void execute() throws ResourceUnavailableException, InsufficientCapacityException, ServerApiException, ConcurrentOperationException,
        ResourceAllocationException, NetworkRuleConflictException {
        try {
            UcsManagerResponse rsp = mgr.addUcsManager(this);
            rsp.setObjectName("ucsmanager");
            rsp.setResponseName(getCommandName());
            this.setResponseObject(rsp);
        } catch (Exception e) {
            s_logger.warn("Exception: ", e);
            throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, e.getMessage());
        }
    }

};