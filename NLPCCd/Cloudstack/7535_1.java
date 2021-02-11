//,temp,sample_9294.java,2,17,temp,sample_9284.java,2,17
//,2
public class xxx {
public void dummy_method(){
if (vm == null) {
sendResponse(resp, "failed");
return;
}
if (vm.getHostId() == null) {
sendResponse(resp, "failed");
return;
}
HostVO host = _ms.getHostBy(vm.getHostId());
if (host == null) {


log.info("vm s host does not exist sending failed response for authentication request from console proxy");
}
}

};