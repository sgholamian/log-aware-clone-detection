//,temp,Xenserver625StorageProcessor.java,965,1028,temp,Xenserver625StorageProcessor.java,913,963
//,3
public class xxx {
    @Override
    public Answer copyVolumeFromPrimaryToSecondary(final CopyCommand cmd) {
        final Connection conn = hypervisorResource.getConnection();
        final VolumeObjectTO srcVolume = (VolumeObjectTO)cmd.getSrcTO();
        final VolumeObjectTO destVolume = (VolumeObjectTO)cmd.getDestTO();
        final int wait = cmd.getWait();
        final DataStoreTO destStore = destVolume.getDataStore();

        if (destStore instanceof NfsTO) {
            SR secondaryStorage = null;
            Task task = null;
            try {
                final NfsTO nfsStore = (NfsTO)destStore;
                final URI uri = new URI(nfsStore.getUrl());
                // Create the volume folder
                if (!hypervisorResource.createSecondaryStorageFolder(conn, uri.getHost() + ":" + uri.getPath(), destVolume.getPath())) {
                    throw new InternalErrorException("Failed to create the volume folder.");
                }

                // Create a SR for the volume UUID folder
                secondaryStorage = createFileSr(conn, uri.getHost() + ":" + uri.getPath(), destVolume.getPath());
                // Look up the volume on the source primary storage pool
                final VDI srcVdi = getVDIbyUuid(conn, srcVolume.getPath());
                // Copy the volume to secondary storage
                task = srcVdi.copyAsync(conn, secondaryStorage, null, null);
                // poll every 1 seconds ,
                hypervisorResource.waitForTask(conn, task, 1000, wait * 1000);
                hypervisorResource.checkForSuccess(conn, task);
                final VDI destVdi = Types.toVDI(task, conn);
                final String destVolumeUUID = destVdi.getUuid(conn);

                final VolumeObjectTO newVol = new VolumeObjectTO();
                newVol.setPath(destVolume.getPath() + File.separator + destVolumeUUID + ".vhd");
                newVol.setSize(srcVolume.getSize());
                return new CopyCmdAnswer(newVol);
            } catch (final Exception e) {
                s_logger.debug("Failed to copy volume to secondary: " + e.toString());
                return new CopyCmdAnswer("Failed to copy volume to secondary: " + e.toString());
            } finally {
                if (task != null) {
                    try {
                        task.destroy(conn);
                    } catch (final Exception e) {
                        s_logger.warn("unable to destroy task(" + task.toWireString() + ") due to " + e.toString());
                    }
                }
                hypervisorResource.removeSR(conn, secondaryStorage);
            }
        }
        return new CopyCmdAnswer("unsupported protocol");
    }

};