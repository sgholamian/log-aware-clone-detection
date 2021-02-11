//,temp,HostMO.java,589,605,temp,VirtualMachineMO.java,589,607
//,3
public class xxx {
    @Override
    public boolean createVm(VirtualMachineConfigSpec vmSpec) throws Exception {
        assert (vmSpec != null);
        DatacenterMO dcMo = new DatacenterMO(_context, getHyperHostDatacenter());
        ManagedObjectReference morPool = getHyperHostOwnerResourcePool();

        ManagedObjectReference morTask = _context.getService().createVMTask(dcMo.getVmFolder(), vmSpec, morPool, _mor);
        boolean result = _context.getVimClient().waitForTask(morTask);

        if (result) {
            _context.waitForTaskProgressDone(morTask);
            return true;
        } else {
            s_logger.error("VMware createVM_Task failed due to " + TaskMO.getTaskFailureInfo(_context, morTask));
        }
        return false;
    }

};