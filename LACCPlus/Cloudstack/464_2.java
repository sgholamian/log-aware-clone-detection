//,temp,Ovm3StorageProcessor.java,302,337,temp,Ovm3StorageProcessor.java,195,241
//,3
public class xxx {
    @Override
    public CopyCmdAnswer copyTemplateToPrimaryStorage(CopyCommand cmd) {
        LOGGER.debug("execute copyTemplateToPrimaryStorage: "+ cmd.getClass());
        DataTO srcData = cmd.getSrcTO();
        DataStoreTO srcStore = srcData.getDataStore();
        DataTO destData = cmd.getDestTO();
        NfsTO srcImageStore = (NfsTO) srcStore;
        TemplateObjectTO destTemplate = (TemplateObjectTO) destData;
        try {
            String secPoolUuid = pool.setupSecondaryStorage(srcImageStore.getUrl());
            String primaryPoolUuid = destData.getDataStore().getUuid();
            String destPath = config.getAgentOvmRepoPath() + "/"
                    + ovmObject.deDash(primaryPoolUuid) + "/"
                    + config.getTemplateDir();
            String sourcePath = config.getAgentSecStoragePath()
                    + "/" + secPoolUuid;
            Linux host = new Linux(c);
            String destUuid = destTemplate.getUuid();
            /*
             * Would love to add dynamic formats (tolower), to also support
             * VHD and QCOW2, although Ovm3.2 does not have tapdisk2 anymore
             * so we can forget about that.
             */
            /* TODO: add checksumming */
            String srcFile = sourcePath + "/"
                    + srcData.getPath();
            if (srcData.getPath().endsWith("/")) {
                srcFile = sourcePath + "/" + srcData.getPath()
                        + "/" + destUuid + ".raw";
            }
            String destFile = destPath + "/" + destUuid + ".raw";
            LOGGER.debug("CopyFrom: " + srcData.getObjectType() + ","
                    + srcFile + " to " + destData.getObjectType() + ","
                    + destFile);
            host.copyFile(srcFile, destFile);
            TemplateObjectTO newVol = new TemplateObjectTO();
            newVol.setUuid(destUuid);
            // was destfile
            newVol.setPath(destUuid);
            newVol.setFormat(ImageFormat.RAW);
            return new CopyCmdAnswer(newVol);
        } catch (Ovm3ResourceException e) {
            String msg = "Error while copying template to primary storage: " + e.getMessage();
            LOGGER.info(msg);
            return new CopyCmdAnswer(msg);
        }
    }

};