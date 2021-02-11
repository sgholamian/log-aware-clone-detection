//,temp,ClusterMO.java,347,372,temp,HostMO.java,589,605
//,3
public class xxx {
    @Override
    public boolean createVm(VirtualMachineConfigSpec vmSpec) throws Exception {
        if (s_logger.isTraceEnabled())
            s_logger.trace("vCenter API trace - createVM_Task(). target MOR: " + _mor.getValue() + ", VirtualMachineConfigSpec: " + new Gson().toJson(vmSpec));

        assert (vmSpec != null);
        DatacenterMO dcMo = new DatacenterMO(_context, getHyperHostDatacenter());
        ManagedObjectReference morPool = getHyperHostOwnerResourcePool();

        ManagedObjectReference morTask = _context.getService().createVMTask(dcMo.getVmFolder(), vmSpec, morPool, null);
        boolean result = _context.getVimClient().waitForTask(morTask);

        if (result) {
            _context.waitForTaskProgressDone(morTask);

            if (s_logger.isTraceEnabled())
                s_logger.trace("vCenter API trace - createVM_Task() done(successfully)");
            return true;
        } else {
            s_logger.error("VMware createVM_Task failed due to " + TaskMO.getTaskFailureInfo(_context, morTask));
        }

        if (s_logger.isTraceEnabled())
            s_logger.trace("vCenter API trace - createVM_Task() done(failed)");
        return false;
    }

};