//,temp,XenServerStorageProcessor.java,1637,1680,temp,XenServerStorageProcessor.java,106,160
//,3
public class xxx {
    @Override
    public SnapshotAndCopyAnswer snapshotAndCopy(final SnapshotAndCopyCommand cmd) {
        final Connection conn = hypervisorResource.getConnection();

        try {
            SR sourceSr = null;

            final Map<String, String> sourceDetails = cmd.getSourceDetails();

            if (sourceDetails != null && sourceDetails.keySet().size() > 0) {
                final String iScsiName = sourceDetails.get(DiskTO.IQN);
                final String storageHost = sourceDetails.get(DiskTO.STORAGE_HOST);
                final String chapInitiatorUsername = sourceDetails.get(DiskTO.CHAP_INITIATOR_USERNAME);
                final String chapInitiatorSecret = sourceDetails.get(DiskTO.CHAP_INITIATOR_SECRET);

                sourceSr = hypervisorResource.getIscsiSR(conn, iScsiName, storageHost, iScsiName, chapInitiatorUsername, chapInitiatorSecret, false);
            }

            final VDI vdiToSnapshot = VDI.getByUuid(conn, cmd.getUuidOfSourceVdi());

            final VDI vdiSnapshot = vdiToSnapshot.snapshot(conn, new HashMap<String, String>());

            final Map<String, String> destDetails = cmd.getDestDetails();

            final String iScsiName = destDetails.get(DiskTO.IQN);
            final String storageHost = destDetails.get(DiskTO.STORAGE_HOST);
            final String chapInitiatorUsername = destDetails.get(DiskTO.CHAP_INITIATOR_USERNAME);
            final String chapInitiatorSecret = destDetails.get(DiskTO.CHAP_INITIATOR_SECRET);

            final SR newSr = hypervisorResource.getIscsiSR(conn, iScsiName, storageHost, iScsiName, chapInitiatorUsername, chapInitiatorSecret, false);

            final VDI vdiCopy = vdiSnapshot.copy(conn, newSr);

            final String vdiUuid = vdiCopy.getUuid(conn);

            vdiSnapshot.destroy(conn);

            if (sourceSr != null) {
                hypervisorResource.removeSR(conn, sourceSr);
            }

            hypervisorResource.removeSR(conn, newSr);

            final SnapshotAndCopyAnswer snapshotAndCopyAnswer = new SnapshotAndCopyAnswer();

            snapshotAndCopyAnswer.setPath(vdiUuid);

            return snapshotAndCopyAnswer;
        }
        catch (final Exception ex) {
            s_logger.warn("Failed to take and copy snapshot: " + ex.toString(), ex);

            return new SnapshotAndCopyAnswer(ex.getMessage());
        }
    }

};