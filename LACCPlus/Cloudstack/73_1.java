//,temp,AddBaremetalPxeCmd.java,80,92,temp,CreateStorageNetworkIpRangeCmd.java,114,126
//,3
public class xxx {
    @Override
    public void execute() throws ResourceUnavailableException, InsufficientCapacityException, ServerApiException, ConcurrentOperationException,
        ResourceAllocationException, NetworkRuleConflictException {
        try {
            BaremetalPxeVO vo = pxeMgr.addPxeServer(this);
            BaremetalPxeResponse rsp = pxeMgr.getApiResponse(vo);
            rsp.setResponseName(getCommandName());
            this.setResponseObject(rsp);
        } catch (Exception e) {
            s_logger.warn("Unable to add external pxe server with url: " + getUrl(), e);
            throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, e.getMessage());
        }
    }

};