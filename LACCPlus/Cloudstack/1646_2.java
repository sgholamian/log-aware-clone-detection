//,temp,LdapUserSearchCmd.java,70,86,temp,DeleteUcsManagerCmd.java,54,67
//,3
public class xxx {
    @Override
    public void execute() throws ResourceUnavailableException, InsufficientCapacityException, ServerApiException, ConcurrentOperationException,
        ResourceAllocationException, NetworkRuleConflictException {
        try {
            mgr.deleteUcsManager(ucsManagerId);
            SuccessResponse rsp = new SuccessResponse();
            rsp.setResponseName(getCommandName());
            rsp.setObjectName("success");
            this.setResponseObject(rsp);
        } catch (Exception e) {
            logger.debug(e.getMessage(), e);
            throw new CloudRuntimeException(e);
        }
    }

};