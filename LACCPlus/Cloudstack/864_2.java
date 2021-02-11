//,temp,VirtualMachineMO.java,537,554,temp,VirtualMachineMO.java,382,393
//,3
public class xxx {
    public boolean reset() throws Exception {
        ManagedObjectReference morTask = _context.getService().resetVMTask(_mor);

        boolean result = _context.getVimClient().waitForTask(morTask);
        if (result) {
            _context.waitForTaskProgressDone(morTask);
            return true;
        } else {
            s_logger.error("VMware resetVM_Task failed due to " + TaskMO.getTaskFailureInfo(_context, morTask));
        }
        return false;
    }

};