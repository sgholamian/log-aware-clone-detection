//,temp,VirtualMachineMO.java,556,572,temp,VirtualMachineMO.java,438,448
//,3
public class xxx {
    public boolean changeHost(VirtualMachineRelocateSpec relocateSpec) throws Exception {
        ManagedObjectReference morTask = _context.getService().relocateVMTask(_mor, relocateSpec, VirtualMachineMovePriority.DEFAULT_PRIORITY);
        boolean result = _context.getVimClient().waitForTask(morTask);
        if (result) {
            _context.waitForTaskProgressDone(morTask);
            return true;
        } else {
            s_logger.error("VMware RelocateVM_Task to change host failed due to " + TaskMO.getTaskFailureInfo(_context, morTask));
        }
        return false;
    }

};