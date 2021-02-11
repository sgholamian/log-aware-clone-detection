//,temp,sample_6348.java,2,10,temp,sample_6347.java,2,9
//,3
public class xxx {
public synchronized VirtualMachineMO findVmOnHyperHost(String vmName) throws Exception {
VirtualMachineMO vmMo = _vmCache.get(vmName);
if (vmMo != null) {
return vmMo;
}


log.info("vm not found in host cache");
}

};