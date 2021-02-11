//,temp,sample_489.java,2,13,temp,sample_2317.java,2,13
//,2
public class xxx {
public void execute() throws ResourceUnavailableException, InsufficientCapacityException, ServerApiException, ConcurrentOperationException, ResourceAllocationException, NetworkRuleConflictException {
try {
BaremetalDhcpVO vo = mgr.addDchpServer(this);
BaremetalDhcpResponse response = mgr.generateApiResponse(vo);
response.setResponseName(getCommandName());
this.setResponseObject(response);
} catch (Exception e) {


log.info("unable to add external dhcp server with url");
}
}

};