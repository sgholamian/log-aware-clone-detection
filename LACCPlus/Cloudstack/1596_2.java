//,temp,VmwareResource.java,3924,3935,temp,VmwareResource.java,3912,3922
//,3
public class xxx {
    private ManagedObjectReference getDataCenterMOReference(String vmName, VmwareHypervisorHost hyperHost) {
        ManagedObjectReference morDc;
        try {
            morDc = hyperHost.getHyperHostDatacenter();
        } catch (Exception e) {
            String msg = "exception while finding VMware datacenter to search for VM " + vmName;
            s_logger.error(msg);
            throw new CloudRuntimeException(msg + ": " + e.getLocalizedMessage());
        }
        return morDc;
    }

};