//,temp,AddHostCmd.java,139,162,temp,ListBaremetalRctCmd.java,44,62
//,3
public class xxx {
    @Override
    public void execute() throws ResourceUnavailableException, InsufficientCapacityException, ServerApiException, ConcurrentOperationException,
        ResourceAllocationException, NetworkRuleConflictException {
        try {
            ListResponse<BaremetalRctResponse> response = new ListResponse<>();
            List<BaremetalRctResponse> rctResponses = new ArrayList<>();
            BaremetalRctResponse rsp = vlanMgr.listRct();
            if (rsp != null) {
                rctResponses.add(rsp);
            }
            response.setResponses(rctResponses);
            response.setResponseName(getCommandName());
            response.setObjectName("baremetalrcts");
            this.setResponseObject(response);
        } catch (Exception e) {
            s_logger.debug("Exception happened while executing ListBaremetalRctCmd", e);
            throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, e.getMessage());
        }
    }

};