//,temp,sample_5568.java,2,10,temp,sample_375.java,2,10
//,3
public class xxx {
public void cleanup(Xen.Vm vm) {
try {
cleanupNetwork(vm.getVmVifs());
} catch (XmlRpcException e) {


log.info("clean up network for failed");
}
}

};