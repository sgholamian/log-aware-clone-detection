//,temp,sample_9288.java,2,9,temp,sample_9292.java,2,9
//,2
public class xxx {
private void handleAccessRequest(HttpServletRequest req, HttpServletResponse resp, long vmId) {
VirtualMachine vm = _vmMgr.findById(vmId);
if (vm == null) {


log.info("vm does not exist sending blank response for console access request");
}
}

};