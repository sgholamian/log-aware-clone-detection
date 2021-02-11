//,temp,ListUcsProfileCmd.java,59,71,temp,BaremetalProvisionDoneNotificationCmd.java,59,68
//,3
public class xxx {
    @Override
    public void execute() throws ResourceUnavailableException, InsufficientCapacityException, ServerApiException, ConcurrentOperationException,
        ResourceAllocationException, NetworkRuleConflictException {
        try {
            ListResponse<UcsProfileResponse> response = mgr.listUcsProfiles(this);
            response.setResponseName(getCommandName());
            response.setObjectName("ucsprofiles");
            this.setResponseObject(response);
        } catch (Exception e) {
            s_logger.warn("Exception: ", e);
            throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, e.getMessage());
        }
    }

};