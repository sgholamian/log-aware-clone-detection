//,temp,BaseMO.java,94,105,temp,BaseMO.java,77,88
//,3
public class xxx {
    public boolean destroy() throws Exception {
        ManagedObjectReference morTask = _context.getService().destroyTask(_mor);

        boolean result = _context.getVimClient().waitForTask(morTask);
        if (result) {
            _context.waitForTaskProgressDone(morTask);
            return true;
        } else {
            s_logger.error("VMware destroy_Task failed due to " + TaskMO.getTaskFailureInfo(_context, morTask));
        }
        return false;
    }

};