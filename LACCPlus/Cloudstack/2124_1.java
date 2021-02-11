//,temp,Xenserver625StorageProcessor.java,965,1028,temp,Xenserver625StorageProcessor.java,683,768
//,3
public class xxx {
    @Override
    public Answer copyVolumeFromImageCacheToPrimary(final CopyCommand cmd) {
        final Connection conn = hypervisorResource.getConnection();
        final DataTO srcData = cmd.getSrcTO();
        final DataTO destData = cmd.getDestTO();
        final int wait = cmd.getWait();
        final VolumeObjectTO srcVolume = (VolumeObjectTO)srcData;
        final VolumeObjectTO destVolume = (VolumeObjectTO)destData;
        final PrimaryDataStoreTO primaryStore = (PrimaryDataStoreTO)destVolume.getDataStore();
        final DataStoreTO srcStore = srcVolume.getDataStore();

        if (srcStore instanceof NfsTO) {
            final NfsTO nfsStore = (NfsTO)srcStore;
            final String volumePath = srcVolume.getPath();
            int index = volumePath.lastIndexOf("/");
            final String volumeDirectory = volumePath.substring(0, index);
            String volumeUuid = volumePath.substring(index + 1);
            index = volumeUuid.indexOf(".");
            if (index != -1) {
                volumeUuid = volumeUuid.substring(0, index);
            }
            URI uri = null;
            try {
                uri = new URI(nfsStore.getUrl());
            } catch (final Exception e) {
                return new CopyCmdAnswer(e.toString());
            }
            final SR srcSr = createFileSr(conn, uri.getHost() + ":" + uri.getPath(), volumeDirectory);
            Task task = null;
            try {
                final SR primaryStoragePool = hypervisorResource.getStorageRepository(conn, primaryStore.getUuid());
                final VDI srcVdi = VDI.getByUuid(conn, volumeUuid);
                task = srcVdi.copyAsync(conn, primaryStoragePool, null, null);
                // poll every 1 seconds ,
                hypervisorResource.waitForTask(conn, task, 1000, wait * 1000);
                hypervisorResource.checkForSuccess(conn, task);
                final VDI destVdi = Types.toVDI(task, conn);
                final VolumeObjectTO newVol = new VolumeObjectTO();
                destVdi.setNameLabel(conn, srcVolume.getName());
                newVol.setPath(destVdi.getUuid(conn));
                newVol.setSize(srcVolume.getSize());

                return new CopyCmdAnswer(newVol);
            } catch (final Exception e) {
                final String msg = "Catch Exception " + e.getClass().getName() + " due to " + e.toString();
                s_logger.warn(msg, e);
                return new CopyCmdAnswer(e.toString());
            } finally {
                if (task != null) {
                    try {
                        task.destroy(conn);
                    } catch (final Exception e) {
                        s_logger.warn("unable to destroy task(" + task.toString() + ") due to " + e.toString());
                    }
                }
                if (srcSr != null) {
                    hypervisorResource.removeSR(conn, srcSr);
                }
            }
        }

        s_logger.debug("unsupported protocol");
        return new CopyCmdAnswer("unsupported protocol");
    }

};