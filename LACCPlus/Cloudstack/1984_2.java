//,temp,Ovm3StorageProcessor.java,726,761,temp,Ovm3StorageProcessor.java,253,282
//,3
public class xxx {
    @Override
    public CopyCmdAnswer cloneVolumeFromBaseTemplate(CopyCommand cmd) {
        LOGGER.debug("execute cloneVolumeFromBaseTemplate: "+ cmd.getClass());
        try {
            // src
            DataTO srcData = cmd.getSrcTO();
            TemplateObjectTO src = (TemplateObjectTO) srcData;
            String srcFile = getVirtualDiskPath(src.getUuid(), src.getDataStore().getUuid());
            srcFile = srcFile.replace(config.getVirtualDiskDir(), config.getTemplateDir());

            DataTO destData = cmd.getDestTO();
            VolumeObjectTO dest = (VolumeObjectTO) destData;
            String destFile = getVirtualDiskPath(dest.getUuid(), dest.getDataStore().getUuid());
            Linux host = new Linux(c);
            LOGGER.debug("CopyFrom: " + srcData.getObjectType() + ","
                    + srcFile + " to " + destData.getObjectType() + ","
                    + destFile);
            host.copyFile(srcFile, destFile);
            VolumeObjectTO newVol = new VolumeObjectTO();
            newVol.setUuid(dest.getUuid());
            // was destfile
            newVol.setPath(dest.getUuid());
            newVol.setFormat(ImageFormat.RAW);
            return new CopyCmdAnswer(newVol);
        } catch (Ovm3ResourceException e) {
            String msg = "Error cloneVolumeFromBaseTemplate: " + e.getMessage();
            LOGGER.info(msg);
            return new CopyCmdAnswer(msg);
        }
    }

};