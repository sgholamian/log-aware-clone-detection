//,temp,Ovm3StorageProcessor.java,302,337,temp,Ovm3StorageProcessor.java,195,241
//,3
public class xxx {
    @Override
    public Answer createTemplateFromSnapshot(CopyCommand cmd) {
        LOGGER.debug("execute createTemplateFromSnapshot: "+ cmd.getClass());
        try {
            // src.getPath contains the uuid of the snapshot.
            DataTO srcData = cmd.getSrcTO();
            SnapshotObjectTO srcSnap = (SnapshotObjectTO) srcData;
            String secPoolUuid = pool.setupSecondaryStorage(srcData.getDataStore().getUrl());
            String srcFile = config.getAgentSecStoragePath()
                    + "/" + secPoolUuid + "/"
                    + srcSnap.getPath();
            // dest
            DataTO destData = cmd.getDestTO();
            TemplateObjectTO destTemplate = (TemplateObjectTO) destData;
            String secPoolUuidTemplate = pool.setupSecondaryStorage(destData.getDataStore().getUrl());
            String destDir = config.getAgentSecStoragePath()
                    + "/" + secPoolUuidTemplate + "/"
                    + destTemplate.getPath();
            String destFile = destDir + "/"
                    + destTemplate.getUuid() + ".raw";
            CloudstackPlugin csp = new CloudstackPlugin(c);
            csp.ovsMkdirs(destDir);

            Linux host = new Linux(c);
            host.copyFile(srcFile, destFile);
            TemplateObjectTO newVol = new TemplateObjectTO();
            newVol.setUuid(destTemplate.getUuid());
            newVol.setPath(destTemplate.getUuid());
            newVol.setFormat(ImageFormat.RAW);
            return new CopyCmdAnswer(newVol);
        } catch (Ovm3ResourceException e) {
            String msg = "Error backupSnapshot: " + e.getMessage();
            LOGGER.info(msg);
            return new CopyCmdAnswer(msg);
        }
    }

};