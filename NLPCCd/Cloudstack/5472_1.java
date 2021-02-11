//,temp,sample_6781.java,2,12,temp,sample_4761.java,2,13
//,3
public class xxx {
public void execute() throws ResourceUnavailableException, InsufficientCapacityException, ServerApiException, ConcurrentOperationException, ResourceAllocationException, NetworkRuleConflictException {
try {
UcsBladeResponse rsp = mgr.associateProfileToBlade(this);
rsp.setResponseName(getCommandName());
this.setResponseObject(rsp);
} catch (Exception e) {


log.info("exception");
}
}

};