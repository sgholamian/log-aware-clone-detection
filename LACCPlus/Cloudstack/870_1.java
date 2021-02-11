//,temp,VirtualMachineMO.java,556,572,temp,VirtualMachineMO.java,537,554
//,3
public class xxx {
    public boolean revertToSnapshot(String snapshotName) throws Exception {
        ManagedObjectReference morSnapshot = getSnapshotMor(snapshotName);
        if (morSnapshot == null) {
            s_logger.warn("Unable to find snapshot: " + snapshotName);
            return false;
        }
        ManagedObjectReference morTask = _context.getService().revertToSnapshotTask(morSnapshot, _mor, null);
        boolean result = _context.getVimClient().waitForTask(morTask);
        if (result) {
            _context.waitForTaskProgressDone(morTask);
            return true;
        } else {
            s_logger.error("VMware revert to snapshot failed due to " + TaskMO.getTaskFailureInfo(_context, morTask));
        }

        return false;
    }

};