//,temp,sample_712.java,2,13,temp,sample_7770.java,2,13
//,2
public class xxx {
public void execute() throws ResourceUnavailableException, InsufficientCapacityException, ServerApiException, ConcurrentOperationException, ResourceAllocationException, NetworkRuleConflictException {
try {
ListResponse<UcsManagerResponse> response = mgr.listUcsManager(this);
response.setResponseName(getCommandName());
response.setObjectName("ucsmanager");
this.setResponseObject(response);
} catch (Exception e) {


log.info("exception");
}
}

};