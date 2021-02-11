//,temp,XenServerStorageProcessor.java,882,920,temp,XenServerStorageProcessor.java,848,880
//,3
public class xxx {
    @Override
    public Answer copyVolumeFromPrimaryToSecondary(final CopyCommand cmd) {
        final Connection conn = hypervisorResource.getConnection();
        final VolumeObjectTO srcVolume = (VolumeObjectTO) cmd.getSrcTO();
        final VolumeObjectTO destVolume = (VolumeObjectTO) cmd.getDestTO();
        final int wait = cmd.getWait();
        final DataStoreTO destStore = destVolume.getDataStore();

        if (destStore instanceof NfsTO) {
            SR secondaryStorage = null;
            try {
                final NfsTO nfsStore = (NfsTO) destStore;
                final URI uri = new URI(nfsStore.getUrl());
                // Create the volume folder
                if (!hypervisorResource.createSecondaryStorageFolder(conn, uri.getHost() + ":" + uri.getPath(), destVolume.getPath())) {
                    throw new InternalErrorException("Failed to create the volume folder.");
                }

                // Create a SR for the volume UUID folder
                secondaryStorage = hypervisorResource.createNfsSRbyURI(conn, new URI(nfsStore.getUrl() + nfsStore.getPathSeparator() + destVolume.getPath()), false);
                // Look up the volume on the source primary storage pool
                final VDI srcVdi = getVDIbyUuid(conn, srcVolume.getPath());
                // Copy the volume to secondary storage
                final VDI destVdi = hypervisorResource.cloudVDIcopy(conn, srcVdi, secondaryStorage, wait);
                final String destVolumeUUID = destVdi.getUuid(conn);

                final VolumeObjectTO newVol = new VolumeObjectTO();
                newVol.setPath(destVolume.getPath() + nfsStore.getPathSeparator() + destVolumeUUID + ".vhd");
                newVol.setSize(srcVolume.getSize());
                return new CopyCmdAnswer(newVol);
            } catch (final Exception e) {
                s_logger.debug("Failed to copy volume to secondary: " + e.toString());
                return new CopyCmdAnswer("Failed to copy volume to secondary: " + e.toString());
            } finally {
                hypervisorResource.removeSR(conn, secondaryStorage);
            }
        }
        return new CopyCmdAnswer("unsupported protocol");
    }

};