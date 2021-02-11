//,temp,sample_9289.java,2,13,temp,sample_9283.java,2,13
//,2
public class xxx {
private void handleThumbnailRequest(HttpServletRequest req, HttpServletResponse resp, long vmId) {
VirtualMachine vm = _vmMgr.findById(vmId);
if (vm == null) {
sendResponse(resp, "");
return;
}
if (vm.getHostId() == null) {


log.info("vm lost host info sending blank response for thumbnail request");
}
}

};