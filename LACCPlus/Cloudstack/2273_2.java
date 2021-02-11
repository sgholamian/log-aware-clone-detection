//,temp,VirtualMachineMO.java,426,436,temp,VirtualMachineMO.java,412,424
//,3
public class xxx {
    public boolean migrate(ManagedObjectReference morRp, ManagedObjectReference morTargetHost) throws Exception {
        ManagedObjectReference morTask = _context.getService().migrateVMTask(_mor, morRp, morTargetHost, VirtualMachineMovePriority.DEFAULT_PRIORITY, null);

        boolean result = _context.getVimClient().waitForTask(morTask);
        if (result) {
            _context.waitForTaskProgressDone(morTask);
            return true;
        } else {
            s_logger.error("VMware migrateVM_Task failed due to " + TaskMO.getTaskFailureInfo(_context, morTask));
        }

        return false;
    }

};