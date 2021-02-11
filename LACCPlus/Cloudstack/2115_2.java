//,temp,NfsSecondaryStorageResource.java,1401,1451,temp,NfsSecondaryStorageResource.java,1288,1339
//,3
public class xxx {
    protected Answer copyFromNfsToS3(CopyCommand cmd) {
        final DataTO srcData = cmd.getSrcTO();
        final DataTO destData = cmd.getDestTO();
        DataStoreTO srcDataStore = srcData.getDataStore();
        NfsTO srcStore = (NfsTO)srcDataStore;
        DataStoreTO destDataStore = destData.getDataStore();

        final S3TO s3 = (S3TO)destDataStore;

        try {
            final String templatePath = determineStorageTemplatePath(srcStore.getUrl(), srcData.getPath(), _nfsVersion);

            if (s_logger.isDebugEnabled()) {
                s_logger.debug("Found " + srcData.getObjectType() + " from directory " + templatePath + " to upload to S3.");
            }

            final String bucket = s3.getBucketName();
            File srcFile = findFile(templatePath);
            if (srcFile == null) {
                return new CopyCmdAnswer("Can't find src file:" + templatePath);
            }

            ImageFormat format = getTemplateFormat(srcFile.getName());
            String key = destData.getPath() + S3Utils.SEPARATOR + srcFile.getName();

            putFile(s3, srcFile, bucket, key).waitForCompletion();

            DataTO retObj = null;
            if (destData.getObjectType() == DataObjectType.TEMPLATE) {
                TemplateObjectTO newTemplate = new TemplateObjectTO();
                newTemplate.setPath(key);
                newTemplate.setSize(getVirtualSize(srcFile, format));
                newTemplate.setPhysicalSize(srcFile.length());
                newTemplate.setFormat(format);
                retObj = newTemplate;
            } else if (destData.getObjectType() == DataObjectType.VOLUME) {
                VolumeObjectTO newVol = new VolumeObjectTO();
                newVol.setPath(key);
                newVol.setSize(srcFile.length());
                retObj = newVol;
            } else if (destData.getObjectType() == DataObjectType.SNAPSHOT) {
                SnapshotObjectTO newSnapshot = new SnapshotObjectTO();
                newSnapshot.setPath(key);
                retObj = newSnapshot;
            }

            return new CopyCmdAnswer(retObj);
        } catch (Exception e) {
            s_logger.error("failed to upload" + srcData.getPath(), e);
            return new CopyCmdAnswer("failed to upload" + srcData.getPath() + e.toString());
        }
    }

};