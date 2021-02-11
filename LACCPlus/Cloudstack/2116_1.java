//,temp,NfsSecondaryStorageResource.java,1870,1936,temp,NfsSecondaryStorageResource.java,1604,1676
//,3
public class xxx {
    protected Answer deleteSnapshot(final DeleteCommand cmd) {
        DataTO obj = cmd.getData();
        DataStoreTO dstore = obj.getDataStore();
        if (dstore instanceof NfsTO) {
            NfsTO nfs = (NfsTO)dstore;
            String parent = getRootDir(nfs.getUrl(), _nfsVersion);
            if (!parent.endsWith(File.separator)) {
                parent += File.separator;
            }
            String snapshotPath = obj.getPath();
            if (snapshotPath.startsWith(File.separator)) {
                snapshotPath = snapshotPath.substring(1);
            }
            // check if the passed snapshot path is a directory or not. For ImageCache, path is stored as a directory instead of
            // snapshot file name. If so, since backupSnapshot process has already deleted snapshot in cache, so we just do nothing
            // and return true.
            String fullSnapPath = parent + snapshotPath;
            File snapDir = new File(fullSnapPath);
            if (snapDir.exists() && snapDir.isDirectory()) {
                s_logger.debug("snapshot path " + snapshotPath + " is a directory, already deleted during backup snapshot, so no need to delete");
                return new Answer(cmd, true, null);
            }
            // passed snapshot path is a snapshot file path, then get snapshot directory first
            int index = snapshotPath.lastIndexOf("/");
            String snapshotName = snapshotPath.substring(index + 1);
            snapshotPath = snapshotPath.substring(0, index);
            String absoluteSnapshotPath = parent + snapshotPath;
            // check if snapshot directory exists
            File snapshotDir = new File(absoluteSnapshotPath);
            String details = null;
            if (!snapshotDir.exists()) {
                details = "snapshot directory " + snapshotDir.getName() + " doesn't exist";
                s_logger.debug(details);
                return new Answer(cmd, true, details);
            }
            // delete snapshot in the directory if exists
            String lPath = absoluteSnapshotPath + "/*" + snapshotName + "*";
            String result = deleteLocalFile(lPath);
            if (result != null) {
                details = "failed to delete snapshot " + lPath + " , err=" + result;
                s_logger.warn(details);
                return new Answer(cmd, false, details);
            }
            return new Answer(cmd, true, null);
        } else if (dstore instanceof S3TO) {
            final S3TO s3 = (S3TO)dstore;
            final String path = obj.getPath();
            final String bucket = s3.getBucketName();
            try {
                S3Utils.deleteObject(s3, bucket, path);
                return new Answer(cmd, true, String.format("Deleted snapshot %1%s from bucket %2$s.", path, bucket));
            } catch (Exception e) {
                final String errorMessage = String.format("Failed to delete snapshot %1$s from bucket %2$s due to the following error: %3$s", path, bucket, e.getMessage());
                s_logger.error(errorMessage, e);
                return new Answer(cmd, false, errorMessage);
            }
        } else if (dstore instanceof SwiftTO) {
            SwiftTO swiftTO = (SwiftTO)dstore;
            String path = obj.getPath();
            SwiftUtil.deleteObject(swiftTO, path);

            return new Answer(cmd, true, "Deleted snapshot " + path + " from swift");
        } else {
            return new Answer(cmd, false, "Unsupported image data store: " + dstore);
        }

    }

};