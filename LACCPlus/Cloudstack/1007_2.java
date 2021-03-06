//,temp,DatastoreMO.java,157,172,temp,BaseMO.java,94,105
//,3
public class xxx {
    public boolean rename(String newName) throws Exception {
        ManagedObjectReference morTask = _context.getService().renameTask(_mor, newName);

        boolean result = _context.getVimClient().waitForTask(morTask);
        if (result) {
            _context.waitForTaskProgressDone(morTask);
            return true;
        } else {
            s_logger.error("VMware rename_Task failed due to " + TaskMO.getTaskFailureInfo(_context, morTask));
        }
        return false;
    }

};