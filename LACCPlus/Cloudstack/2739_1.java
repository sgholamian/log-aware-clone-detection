//,temp,XenServerStorageProcessor.java,1682,1725,temp,XenServerStorageProcessor.java,1637,1680
//,3
public class xxx {
    Answer createNonManagedVolumeFromManagedSnapshot(final CopyCommand cmd) {
        Connection conn = hypervisorResource.getConnection();
        SR srcSr = null;

        try {
            Map<String, String> srcOptions = cmd.getOptions();

            String src_iScsiName = srcOptions.get(DiskTO.IQN);
            String srcStorageHost = srcOptions.get(DiskTO.STORAGE_HOST);
            String srcChapInitiatorUsername = srcOptions.get(DiskTO.CHAP_INITIATOR_USERNAME);
            String srcChapInitiatorSecret = srcOptions.get(DiskTO.CHAP_INITIATOR_SECRET);

            srcSr = hypervisorResource.getIscsiSR(conn, src_iScsiName, srcStorageHost, src_iScsiName,
                    srcChapInitiatorUsername, srcChapInitiatorSecret, false);

            // there should only be one VDI in this SR
            VDI srcVdi = srcSr.getVDIs(conn).iterator().next();

            DataTO destData = cmd.getDestTO();
            String primaryStorageNameLabel = destData.getDataStore().getUuid();

            SR destSr = hypervisorResource.getSRByNameLabelandHost(conn, primaryStorageNameLabel);

            VDI vdiCopy = srcVdi.copy(conn, destSr);

            VolumeObjectTO newVol = new VolumeObjectTO();

            newVol.setSize(vdiCopy.getVirtualSize(conn));
            newVol.setPath(vdiCopy.getUuid(conn));
            newVol.setFormat(ImageFormat.VHD);

            return new CopyCmdAnswer(newVol);
        }
        catch (Exception ex) {
            s_logger.warn("Failed to copy snapshot to volume: " + ex.toString(), ex);

            return new CopyCmdAnswer(ex.getMessage());
        }
        finally {
            if (srcSr != null) {
                hypervisorResource.removeSR(conn, srcSr);
            }
        }
    }

};