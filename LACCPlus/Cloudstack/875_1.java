//,temp,VirtualMachineMO.java,3440,3450,temp,VirtualMachineMO.java,450,465
//,3
public class xxx {
    public boolean consolidateVmDisks() throws Exception {
        ManagedObjectReference morTask = _context.getService().consolidateVMDisksTask(_mor);
        boolean result = _context.getVimClient().waitForTask(morTask);
        if (result) {
            _context.waitForTaskProgressDone(morTask);
            return true;
        } else {
            s_logger.error("VMware ConsolidateVMDisks_Task failed due to " + TaskMO.getTaskFailureInfo(_context, morTask));
        }
        return false;
    }

};