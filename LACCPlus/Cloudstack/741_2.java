//,temp,BaseMO.java,94,105,temp,VirtualMachineMO.java,1040,1051
//,2
public class xxx {
    public boolean configureVm(VirtualMachineConfigSpec vmConfigSpec) throws Exception {
        ManagedObjectReference morTask = _context.getService().reconfigVMTask(_mor, vmConfigSpec);

        boolean result = _context.getVimClient().waitForTask(morTask);
        if (result) {
            _context.waitForTaskProgressDone(morTask);
            return true;
        } else {
            s_logger.error("VMware reconfigVM_Task failed due to " + TaskMO.getTaskFailureInfo(_context, morTask));
        }
        return false;
    }

};