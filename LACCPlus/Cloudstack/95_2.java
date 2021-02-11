//,temp,BaseMO.java,77,88,temp,VirtualMachineMO.java,537,554
//,3
public class xxx {
    public boolean removeSnapshot(String snapshotName, boolean removeChildren) throws Exception {
        ManagedObjectReference morSnapshot = getSnapshotMor(snapshotName);
        if (morSnapshot == null) {
            s_logger.warn("Unable to find snapshot: " + snapshotName);
            return false;
        }

        ManagedObjectReference morTask = _context.getService().removeSnapshotTask(morSnapshot, removeChildren, true);
        boolean result = _context.getVimClient().waitForTask(morTask);
        if (result) {
            _context.waitForTaskProgressDone(morTask);
            return true;
        } else {
            s_logger.error("VMware removeSnapshot_Task failed due to " + TaskMO.getTaskFailureInfo(_context, morTask));
        }

        return false;
    }

};