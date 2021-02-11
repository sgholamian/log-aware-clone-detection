//,temp,sample_9292.java,2,9,temp,sample_9282.java,2,9
//,2
public class xxx {
private void handleThumbnailRequest(HttpServletRequest req, HttpServletResponse resp, long vmId) {
VirtualMachine vm = _vmMgr.findById(vmId);
if (vm == null) {


log.info("vm does not exist sending blank response for thumbnail request");
}
}

};