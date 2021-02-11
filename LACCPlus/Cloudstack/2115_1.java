//,temp,NfsSecondaryStorageResource.java,1401,1451,temp,NfsSecondaryStorageResource.java,1288,1339
//,3
public class xxx {
    protected Answer copyFromNfsToSwift(CopyCommand cmd) {

        final DataTO srcData = cmd.getSrcTO();
        final DataTO destData = cmd.getDestTO();

        DataStoreTO srcDataStore = srcData.getDataStore();
        NfsTO srcStore = (NfsTO)srcDataStore;
        DataStoreTO destDataStore = destData.getDataStore();
        File srcFile = getFile(srcData.getPath(), srcStore.getUrl(), _nfsVersion);

        SwiftTO swift = (SwiftTO)destDataStore;
        long pathId = destData.getId();

        try {

            if (destData instanceof SnapshotObjectTO) {
                pathId = ((SnapshotObjectTO)destData).getVolume().getId();
            }

            String containerName = SwiftUtil.getContainerName(destData.getObjectType().toString(), pathId);
            String swiftPath = SwiftUtil.putObject(swift, srcFile, containerName, srcFile.getName());

            DataTO retObj = null;
            if (destData.getObjectType() == DataObjectType.TEMPLATE) {
                TemplateObjectTO destTemplateData = (TemplateObjectTO)destData;
                String uniqueName = destTemplateData.getName();
                swiftUploadMetadataFile(swift, srcFile, containerName, uniqueName);
                TemplateObjectTO newTemplate = new TemplateObjectTO();
                newTemplate.setPath(swiftPath);
                newTemplate.setSize(getVirtualSize(srcFile, getTemplateFormat(srcFile.getName())));
                newTemplate.setPhysicalSize(srcFile.length());
                newTemplate.setFormat(getTemplateFormat(srcFile.getName()));
                retObj = newTemplate;
            } else if (destData.getObjectType() == DataObjectType.VOLUME) {
                VolumeObjectTO newVol = new VolumeObjectTO();
                newVol.setPath(containerName);
                newVol.setSize(getVirtualSize(srcFile, getTemplateFormat(srcFile.getName())));
                retObj = newVol;
            } else if (destData.getObjectType() == DataObjectType.SNAPSHOT) {
                SnapshotObjectTO newSnapshot = new SnapshotObjectTO();
                newSnapshot.setPath(containerName + File.separator + srcFile.getName());
                retObj = newSnapshot;
            }

            return new CopyCmdAnswer(retObj);

        } catch (Exception e) {
            s_logger.error("failed to upload " + srcData.getPath(), e);
            return new CopyCmdAnswer("failed to upload " + srcData.getPath() + e.toString());
        }
    }

};