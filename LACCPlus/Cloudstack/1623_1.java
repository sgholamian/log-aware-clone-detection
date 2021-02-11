//,temp,VirtualMachineMO.java,426,436,temp,VirtualMachineMO.java,382,393
//,3
public class xxx {
    public boolean changeDatastore(VirtualMachineRelocateSpec relocateSpec) throws Exception {
        ManagedObjectReference morTask = _context.getVimClient().getService().relocateVMTask(_mor, relocateSpec, VirtualMachineMovePriority.DEFAULT_PRIORITY);
        boolean result = _context.getVimClient().waitForTask(morTask);
        if (result) {
            _context.waitForTaskProgressDone(morTask);
            return true;
        } else {
            s_logger.error("VMware RelocateVM_Task to change datastore failed due to " + TaskMO.getTaskFailureInfo(_context, morTask));
        }
        return false;
    }

};