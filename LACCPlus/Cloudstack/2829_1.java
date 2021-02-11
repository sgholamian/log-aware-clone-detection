//,temp,NfsSecondaryStorageResource.java,2339,2444,temp,NfsSecondaryStorageResource.java,2236,2337
//,3
public class xxx {
    protected Answer deleteVolume(final DeleteCommand cmd) {
        DataTO obj = cmd.getData();
        DataStoreTO dstore = obj.getDataStore();
        if (dstore instanceof NfsTO) {
            NfsTO nfs = (NfsTO)dstore;
            String relativeVolumePath = obj.getPath();
            String parent = getRootDir(nfs.getUrl(), _nfsVersion);

            if (relativeVolumePath.startsWith(File.separator)) {
                relativeVolumePath = relativeVolumePath.substring(1);
            }

            if (!parent.endsWith(File.separator)) {
                parent += File.separator;
            }
            String absoluteVolumePath = parent + relativeVolumePath;
            File volPath = new File(absoluteVolumePath);
            File tmpltParent = null;
            if (volPath.exists() && volPath.isDirectory()) {
                // for vmware, absoluteVolumePath represents a directory where volume files are located.
                tmpltParent = volPath;
            } else {
                // for other hypervisors, the volume .vhd or .qcow2 file path is passed
                tmpltParent = new File(absoluteVolumePath).getParentFile();
            }
            String details = null;
            if (!tmpltParent.exists()) {
                details = "volume parent directory " + tmpltParent.getName() + " doesn't exist";
                s_logger.debug(details);
                return new Answer(cmd, true, details);
            }
            File[] tmpltFiles = tmpltParent.listFiles();
            if (tmpltFiles == null || tmpltFiles.length == 0) {
                details = "No files under volume parent directory " + tmpltParent.getName();
                s_logger.debug(details);
            } else {
                boolean found = false;
                for (File f : tmpltFiles) {
                    if (!found && f.getName().equals("volume.properties")) {
                        found = true;
                    }

                    // KVM HA monitor makes a mess in the templates with its
                    // heartbeat tests
                    // Don't let this stop us from cleaning up the template
                    if (f.isDirectory() && f.getName().equals("KVMHA")) {
                        s_logger.debug("Deleting KVMHA directory contents from template location");
                        File[] haFiles = f.listFiles();
                        for (File haFile : haFiles) {
                            haFile.delete();
                        }
                    }

                    if (!f.delete()) {
                        return new Answer(cmd, false, "Unable to delete file " + f.getName() + " under Volume path " + tmpltParent.getPath());
                    }
                }
                if (!found) {
                    details = "Can not find volume.properties under " + tmpltParent.getName();
                    s_logger.debug(details);
                }
            }
            if (!tmpltParent.delete()) {
                details = "Unable to delete directory " + tmpltParent.getName() + " under Volume path " + tmpltParent.getPath();
                s_logger.debug(details);
                return new Answer(cmd, false, details);
            }
            return new Answer(cmd, true, null);
        } else if (dstore instanceof S3TO) {
            final S3TO s3 = (S3TO)dstore;
            final String path = obj.getPath();
            final String bucket = s3.getBucketName();
            try {
                S3Utils.deleteDirectory(s3, bucket, path);
                return new Answer(cmd, true, String.format("Deleted volume %1%s from bucket %2$s.", path, bucket));
            } catch (Exception e) {
                final String errorMessage = String.format("Failed to delete volume %1$s from bucket %2$s due to the following error: %3$s", path, bucket, e.getMessage());
                s_logger.error(errorMessage, e);
                return new Answer(cmd, false, errorMessage);
            }
        } else if (dstore instanceof SwiftTO) {
            Long volumeId = obj.getId();
            String path = obj.getPath();
            String filename = StringUtils.substringAfterLast(path, "/"); // assuming
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
            String result = swiftDelete((SwiftTO)dstore, "V-" + volumeId.toString(), filename);
            if (result != null) {
                String errMsg = "failed to delete volume " + filename + " , err=" + result;
                s_logger.warn(errMsg);
                return new Answer(cmd, false, errMsg);
            }
            return new Answer(cmd, true, "Deleted volume " + path + " from swift");
        } else {
            return new Answer(cmd, false, "Unsupported image data store: " + dstore);
        }

    }

};