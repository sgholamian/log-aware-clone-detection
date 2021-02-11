//,temp,VmwareResource.java,3924,3935,temp,VmwareResource.java,3912,3922
//,3
public class xxx {
    private VirtualMachineMO getVirtualMachineMO(String vmName, VmwareHypervisorHost hyperHost) {
        VirtualMachineMO vmMo = null;
        try {
            // find VM through datacenter (VM is not at the target host yet)
            vmMo = hyperHost.findVmOnPeerHyperHost(vmName);
        } catch (Exception e) {
            String msg = "exception while searching for VM " + vmName + " in VMware datacenter";
            s_logger.error(msg);
            throw new CloudRuntimeException(msg + ": " + e.getLocalizedMessage());
        }
        return vmMo;
    }

};