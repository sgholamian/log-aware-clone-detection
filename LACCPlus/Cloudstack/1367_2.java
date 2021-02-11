//,temp,XenServerStorageProcessor.java,1682,1725,temp,XenServerStorageProcessor.java,162,204
//,3
public class xxx {
    @Override
    public ResignatureAnswer resignature(final ResignatureCommand cmd) {
        SR newSr = null;

        final Connection conn = hypervisorResource.getConnection();

        try {
            final Map<String, String> details = cmd.getDetails();

            final String iScsiName = details.get(DiskTO.IQN);
            final String storageHost = details.get(DiskTO.STORAGE_HOST);
            final String chapInitiatorUsername = details.get(DiskTO.CHAP_INITIATOR_USERNAME);
            final String chapInitiatorSecret = details.get(DiskTO.CHAP_INITIATOR_SECRET);

            newSr = hypervisorResource.getIscsiSR(conn, iScsiName, storageHost, iScsiName, chapInitiatorUsername, chapInitiatorSecret, true, false);

            Set<VDI> vdis = newSr.getVDIs(conn);

            if (vdis.size() != 1) {
                throw new RuntimeException("There were " + vdis.size() + " VDIs in the SR.");
            }

            VDI vdi = vdis.iterator().next();

            final ResignatureAnswer resignatureAnswer = new ResignatureAnswer();

            resignatureAnswer.setSize(vdi.getVirtualSize(conn));
            resignatureAnswer.setPath(vdi.getUuid(conn));
            resignatureAnswer.setFormat(ImageFormat.VHD);

            return resignatureAnswer;
        }
        catch (final Exception ex) {
            s_logger.warn("Failed to resignature: " + ex.toString(), ex);

            return new ResignatureAnswer(ex.getMessage());
        }
        finally {
            if (newSr != null) {
                hypervisorResource.removeSR(conn, newSr);
            }
        }
    }

};