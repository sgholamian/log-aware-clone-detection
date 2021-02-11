//,temp,AddBaremetalDhcpCmd.java,79,91,temp,CreateStorageNetworkIpRangeCmd.java,114,126
//,3
public class xxx {
    @Override
    public void execute() throws ResourceUnavailableException, InsufficientCapacityException, ServerApiException, ConcurrentOperationException,
        ResourceAllocationException, NetworkRuleConflictException {
        try {
            BaremetalDhcpVO vo = mgr.addDchpServer(this);
            BaremetalDhcpResponse response = mgr.generateApiResponse(vo);
            response.setResponseName(getCommandName());
            this.setResponseObject(response);
        } catch (Exception e) {
            s_logger.warn("Unable to add external dhcp server with url: " + getUrl(), e);
            throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, e.getMessage());
        }
    }

};