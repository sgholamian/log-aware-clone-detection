//,temp,sample_489.java,2,13,temp,sample_2317.java,2,13
//,2
public class xxx {
public void execute() throws ResourceUnavailableException, InsufficientCapacityException, ServerApiException, ConcurrentOperationException, ResourceAllocationException, NetworkRuleConflictException {
try {
BaremetalPxeVO vo = pxeMgr.addPxeServer(this);
BaremetalPxeResponse rsp = pxeMgr.getApiResponse(vo);
rsp.setResponseName(getCommandName());
this.setResponseObject(rsp);
} catch (Exception e) {


log.info("unable to add external pxe server with url");
}
}

};