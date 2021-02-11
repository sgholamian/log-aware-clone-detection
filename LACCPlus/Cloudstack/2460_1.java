//,temp,BaremetalProvisionDoneNotificationCmd.java,59,68,temp,DeleteStorageNetworkIpRangeCmd.java,73,84
//,3
public class xxx {
    @Override
    public void execute() throws ResourceUnavailableException, InsufficientCapacityException, ServerApiException, ConcurrentOperationException, ResourceAllocationException, NetworkRuleConflictException {
        try {
            bmMgr.notifyProvisionDone(this);
            this.setResponseObject(new SuccessResponse(getCommandName()));
        } catch (Exception e) {
            s_logger.warn(String.format("unable to notify baremetal provision done[mac:%s]", mac), e);
            throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, e.getMessage());
        }
    }

};