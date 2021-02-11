//,temp,XenServerStorageProcessor.java,1637,1680,temp,XenServerStorageProcessor.java,162,204
//,3
public class xxx {
    Answer createManagedVolumeFromManagedSnapshot(final CopyCommand cmd) {
        try {
            final Connection conn = hypervisorResource.getConnection();

            final Map<String, String> srcOptions = cmd.getOptions();

            final String src_iScsiName = srcOptions.get(DiskTO.IQN);
            final String srcStorageHost = srcOptions.get(DiskTO.STORAGE_HOST);
            final String srcChapInitiatorUsername = srcOptions.get(DiskTO.CHAP_INITIATOR_USERNAME);
            final String srcChapInitiatorSecret = srcOptions.get(DiskTO.CHAP_INITIATOR_SECRET);

            final SR srcSr = hypervisorResource.getIscsiSR(conn, src_iScsiName, srcStorageHost, src_iScsiName, srcChapInitiatorUsername, srcChapInitiatorSecret, false);

            final Map<String, String> destOptions = cmd.getOptions2();

            final String dest_iScsiName = destOptions.get(DiskTO.IQN);
            final String destStorageHost = destOptions.get(DiskTO.STORAGE_HOST);
            final String destChapInitiatorUsername = destOptions.get(DiskTO.CHAP_INITIATOR_USERNAME);
            final String destChapInitiatorSecret = destOptions.get(DiskTO.CHAP_INITIATOR_SECRET);

            final SR destSr = hypervisorResource.getIscsiSR(conn, dest_iScsiName, destStorageHost, dest_iScsiName, destChapInitiatorUsername, destChapInitiatorSecret, false);

            // there should only be one VDI in this SR
            final VDI srcVdi = srcSr.getVDIs(conn).iterator().next();

            final VDI vdiCopy = srcVdi.copy(conn, destSr);

            final VolumeObjectTO newVol = new VolumeObjectTO();

            newVol.setSize(vdiCopy.getVirtualSize(conn));
            newVol.setPath(vdiCopy.getUuid(conn));
            newVol.setFormat(ImageFormat.VHD);

            hypervisorResource.removeSR(conn, srcSr);
            hypervisorResource.removeSR(conn, destSr);

            return new CopyCmdAnswer(newVol);
        }
        catch (final Exception ex) {
            s_logger.warn("Failed to copy snapshot to volume: " + ex.toString(), ex);

            return new CopyCmdAnswer(ex.getMessage());
        }
    }

};