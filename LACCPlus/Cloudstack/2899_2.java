//,temp,ListUcsProfileCmd.java,59,71,temp,ListUcsManagerCmd.java,58,70
//,2
public class xxx {
    @Override
    public void execute() throws ResourceUnavailableException, InsufficientCapacityException, ServerApiException, ConcurrentOperationException,
        ResourceAllocationException, NetworkRuleConflictException {
        try {
            ListResponse<UcsManagerResponse> response = mgr.listUcsManager(this);
            response.setResponseName(getCommandName());
            response.setObjectName("ucsmanager");
            this.setResponseObject(response);
        } catch (Exception e) {
            s_logger.warn("Exception: ", e);
            throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, e.getMessage());
        }
    }

};