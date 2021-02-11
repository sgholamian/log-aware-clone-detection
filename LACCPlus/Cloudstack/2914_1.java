//,temp,DatastoreMO.java,245,271,temp,DatastoreMO.java,217,243
//,2
public class xxx {
    public boolean moveDatastoreFile(String srcFilePath, ManagedObjectReference morSrcDc, ManagedObjectReference morDestDs, String destFilePath,
            ManagedObjectReference morDestDc, boolean forceOverwrite) throws Exception {

        String srcDsName = getName();
        DatastoreMO destDsMo = new DatastoreMO(_context, morDestDs);
        String destDsName = destDsMo.getName();

        ManagedObjectReference morFileManager = _context.getServiceContent().getFileManager();
        String srcFullPath = srcFilePath;
        if (!DatastoreFile.isFullDatastorePath(srcFullPath))
            srcFullPath = String.format("[%s] %s", srcDsName, srcFilePath);

        String destFullPath = destFilePath;
        if (!DatastoreFile.isFullDatastorePath(destFullPath))
            destFullPath = String.format("[%s] %s", destDsName, destFilePath);

        ManagedObjectReference morTask = _context.getService().moveDatastoreFileTask(morFileManager, srcFullPath, morSrcDc, destFullPath, morDestDc, forceOverwrite);

        boolean result = _context.getVimClient().waitForTask(morTask);
        if (result) {
            _context.waitForTaskProgressDone(morTask);
            return true;
        } else {
            s_logger.error("VMware moveDatgastoreFile_Task failed due to " + TaskMO.getTaskFailureInfo(_context, morTask));
        }
        return false;
    }

};