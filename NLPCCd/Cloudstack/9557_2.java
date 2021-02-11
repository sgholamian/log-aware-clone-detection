//,temp,sample_5568.java,2,10,temp,sample_375.java,2,10
//,3
public class xxx {
protected void cleanup(OvmVm.Details vm) {
try {
cleanupNetwork(vm.vifs);
} catch (XmlRpcException e) {


log.info("clean up network for failed");
}
}

};