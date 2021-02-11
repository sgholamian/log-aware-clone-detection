//,temp,NfsSecondaryStorageResource.java,1870,1936,temp,NfsSecondaryStorageResource.java,1604,1676
//,3
public class xxx {
    public Answer execute(DeleteSnapshotsDirCommand cmd) {
        DataStoreTO dstore = cmd.getDataStore();
        if (dstore instanceof NfsTO) {
            NfsTO nfs = (NfsTO)dstore;
            String relativeSnapshotPath = cmd.getDirectory();
            String parent = getRootDir(nfs.getUrl(), _nfsVersion);

            if (relativeSnapshotPath.startsWith(File.separator)) {
                relativeSnapshotPath = relativeSnapshotPath.substring(1);
            }

            if (!parent.endsWith(File.separator)) {
                parent += File.separator;
            }
            String absoluteSnapshotPath = parent + relativeSnapshotPath;
            File snapshotDir = new File(absoluteSnapshotPath);
            String details = null;
            if (!snapshotDir.exists()) {
                details = "snapshot directory " + snapshotDir.getName() + " doesn't exist";
                s_logger.debug(details);
                return new Answer(cmd, true, details);
            }
            // delete all files in the directory
            String lPath = absoluteSnapshotPath + "/*";
            String result = deleteLocalFile(lPath);
            if (result != null) {
                String errMsg = "failed to delete all snapshots " + lPath + " , err=" + result;
                s_logger.warn(errMsg);
                return new Answer(cmd, false, errMsg);
            }
            // delete the directory
            if (!snapshotDir.delete()) {
                details = "Unable to delete directory " + snapshotDir.getName() + " under snapshot path " + relativeSnapshotPath;
                s_logger.debug(details);
                return new Answer(cmd, false, details);
            }
            return new Answer(cmd, true, null);
        } else if (dstore instanceof S3TO) {
            final S3TO s3 = (S3TO)dstore;
            final String path = cmd.getDirectory();
            final String bucket = s3.getBucketName();
            try {
                S3Utils.deleteDirectory(s3, bucket, path);
                return new Answer(cmd, true, String.format("Deleted snapshot %1%s from bucket %2$s.", path, bucket));
            } catch (Exception e) {
                final String errorMessage = String.format("Failed to delete snapshot %1$s from bucket %2$s due to the following error: %3$s", path, bucket, e.getMessage());
                s_logger.error(errorMessage, e);
                return new Answer(cmd, false, errorMessage);
            }
        } else if (dstore instanceof SwiftTO) {
            String path = cmd.getDirectory();
            String volumeId = StringUtils.substringAfterLast(path, "/"); // assuming
            // that
            // the
            // filename
            // is
            // the
            // last
            // section
            // in
            // the
            // path
            String result = swiftDelete((SwiftTO)dstore, "V-" + volumeId.toString(), "");
            if (result != null) {
                String errMsg = "failed to delete snapshot for volume " + volumeId + " , err=" + result;
                s_logger.warn(errMsg);
                return new Answer(cmd, false, errMsg);
            }
            return new Answer(cmd, true, "Deleted snapshot " + path + " from swift");
        } else {
            return new Answer(cmd, false, "Unsupported image data store: " + dstore);
        }
    }

};