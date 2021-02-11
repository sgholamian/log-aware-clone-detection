//,temp,DeleteBaremetalRctCmd.java,60,70,temp,DeleteSecurityGroupCmd.java,122,136
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