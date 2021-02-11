//,temp,VirtualMachineMO.java,589,607,temp,VirtualMachineMO.java,467,482
//,3
public class xxx {
    public boolean removeAllSnapshots() throws Exception {
        VirtualMachineSnapshotInfo snapshotInfo = getSnapshotInfo();

        if (snapshotInfo != null && snapshotInfo.getRootSnapshotList() != null) {
            List<VirtualMachineSnapshotTree> tree = snapshotInfo.getRootSnapshotList();
            for (VirtualMachineSnapshotTree treeNode : tree) {
                ManagedObjectReference morTask = _context.getService().removeSnapshotTask(treeNode.getSnapshot(), true, true);
                boolean result = _context.getVimClient().waitForTask(morTask);
                if (result) {
                    _context.waitForTaskProgressDone(morTask);
                } else {
                    s_logger.error("VMware removeSnapshot_Task failed due to " + TaskMO.getTaskFailureInfo(_context, morTask));
                    return false;
                }
            }
        }

        return true;
    }

};