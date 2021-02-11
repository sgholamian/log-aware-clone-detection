//,temp,HostMO.java,1045,1056,temp,VirtualMachineMO.java,1024,1038
//,3
public class xxx {
    public boolean revertToSnapshot(ManagedObjectReference morSnapshot) throws Exception {
        ManagedObjectReference morTask = _context.getService().revertToSnapshotTask(morSnapshot, _mor, false);
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