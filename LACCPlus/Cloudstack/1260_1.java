//,temp,Ovm3StorageProcessor.java,726,761,temp,Ovm3StorageProcessor.java,195,241
//,3
public class xxx {
    @Override
    public Answer createVolumeFromSnapshot(CopyCommand cmd) {
        LOGGER.debug("execute createVolumeFromSnapshot: "+ cmd.getClass());
        try {
            DataTO srcData = cmd.getSrcTO();
            DataStoreTO srcStore = srcData.getDataStore();
            NfsTO srcImageStore = (NfsTO) srcStore;

            // source, should contain snap dir/filename
            SnapshotObjectTO srcSnap = (SnapshotObjectTO) srcData;
            String secPoolUuid = pool.setupSecondaryStorage(srcImageStore.getUrl());
            String srcFile = config.getAgentSecStoragePath()
                    + "/" + secPoolUuid + "/"
                    + srcSnap.getPath();

            // dest
            DataTO destData = cmd.getDestTO();
            VolumeObjectTO destVol = (VolumeObjectTO) destData;
            String primaryPoolUuid = destData.getDataStore().getUuid();
            String destFile = getVirtualDiskPath(destVol.getUuid(), ovmObject.deDash(primaryPoolUuid));

            Linux host = new Linux(c);
            host.copyFile(srcFile, destFile);

            VolumeObjectTO newVol = new VolumeObjectTO();
            newVol.setUuid(destVol.getUuid());
            // newVol.setPath(destFile);
            newVol.setPath(destVol.getUuid());
            newVol.setFormat(ImageFormat.RAW);
            return new CopyCmdAnswer(newVol);
            /* we assume the cache for templates is local */
        } catch (Ovm3ResourceException e) {
            LOGGER.debug("Failed to createVolumeFromSnapshot: ", e);
            return new CopyCmdAnswer(e.toString());
        }
    }

};