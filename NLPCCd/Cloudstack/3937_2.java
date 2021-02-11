//,temp,sample_712.java,2,13,temp,sample_7770.java,2,13
//,2
public class xxx {
public void execute() throws ResourceUnavailableException, InsufficientCapacityException, ServerApiException, ConcurrentOperationException, ResourceAllocationException, NetworkRuleConflictException {
try {
ListResponse<UcsProfileResponse> response = mgr.listUcsProfiles(this);
response.setResponseName(getCommandName());
response.setObjectName("ucsprofiles");
this.setResponseObject(response);
} catch (Exception e) {


log.info("exception");
}
}

};