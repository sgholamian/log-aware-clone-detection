//,temp,XenServerStorageProcessor.java,882,920,temp,XenServerStorageProcessor.java,848,880
//,3
public class xxx {
    @Override
    public Answer copyVolumeFromImageCacheToPrimary(final CopyCommand cmd) {
        final Connection conn = hypervisorResource.getConnection();
        final DataTO srcData = cmd.getSrcTO();
        final DataTO destData = cmd.getDestTO();
        final int wait = cmd.getWait();
        final VolumeObjectTO srcVolume = (VolumeObjectTO) srcData;
        final VolumeObjectTO destVolume = (VolumeObjectTO) destData;
        final DataStoreTO srcStore = srcVolume.getDataStore();

        if (srcStore instanceof NfsTO) {
            final NfsTO nfsStore = (NfsTO) srcStore;
            try {
                final SR primaryStoragePool = hypervisorResource.getStorageRepository(conn, destVolume.getDataStore().getUuid());
                final String srUuid = primaryStoragePool.getUuid(conn);
                final URI uri = new URI(nfsStore.getUrl());
                final String volumePath = uri.getHost() + ":" + uri.getPath() + nfsStore.getPathSeparator() + srcVolume.getPath();
                final String uuid = copy_vhd_from_secondarystorage(conn, volumePath, srUuid, wait);
                final VolumeObjectTO newVol = new VolumeObjectTO();
                newVol.setPath(uuid);
                newVol.setSize(srcVolume.getSize());

                return new CopyCmdAnswer(newVol);
            } catch (final Exception e) {
                final String msg = "Catch Exception " + e.getClass().getName() + " due to " + e.toString();
                s_logger.warn(msg, e);
                return new CopyCmdAnswer(e.toString());
            }
        }

        s_logger.debug("unsupported protocol");
        return new CopyCmdAnswer("unsupported protocol");
    }

};