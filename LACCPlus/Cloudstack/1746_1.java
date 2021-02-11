//,temp,ListBaremetalDhcpCmd.java,83,98,temp,DeleteUcsManagerCmd.java,54,67
//,3
public class xxx {
    @Override
    public void execute() throws ResourceUnavailableException, InsufficientCapacityException, ServerApiException, ConcurrentOperationException,
        ResourceAllocationException, NetworkRuleConflictException {
        try {
            ListResponse<BaremetalDhcpResponse> response = new ListResponse<BaremetalDhcpResponse>();
            List<BaremetalDhcpResponse> dhcpResponses = _dhcpMgr.listBaremetalDhcps(this);
            response.setResponses(dhcpResponses);
            response.setResponseName(getCommandName());
            response.setObjectName("baremetaldhcps");
            this.setResponseObject(response);
        } catch (Exception e) {
            s_logger.debug("Exception happend while executing ListBaremetalDhcpCmd");
            throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, e.getMessage());
        }

    }

};