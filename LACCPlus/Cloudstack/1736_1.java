//,temp,DatastoreMO.java,157,172,temp,VirtualMachineMO.java,426,436
//,3
public class xxx {
    public boolean deleteFolder(String folder, ManagedObjectReference morDc) throws Exception {
        ManagedObjectReference morFileManager = _context.getServiceContent().getFileManager();
        ManagedObjectReference morTask = _context.getService().deleteDatastoreFileTask(morFileManager, folder, morDc);

        boolean result = _context.getVimClient().waitForTask(morTask);

        if (result) {
            _context.waitForTaskProgressDone(morTask);

            return true;
        } else {
            s_logger.error("VMware deleteDatastoreFile_Task failed due to " + TaskMO.getTaskFailureInfo(_context, morTask));
        }

        return false;
    }

};