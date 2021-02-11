//,temp,Xenserver625StorageProcessor.java,1030,1173,temp,Xenserver625StorageProcessor.java,780,911
//,3
public class xxx {
    @Override
    public Answer createVolumeFromSnapshot(final CopyCommand cmd) {
        final Connection conn = hypervisorResource.getConnection();
        final DataTO srcData = cmd.getSrcTO();
        final SnapshotObjectTO snapshot = (SnapshotObjectTO)srcData;
        final DataTO destData = cmd.getDestTO();
        final PrimaryDataStoreTO pool = (PrimaryDataStoreTO)destData.getDataStore();
        final VolumeObjectTO volume = (VolumeObjectTO)destData;
        final DataStoreTO imageStore = srcData.getDataStore();

        if (isCreateManagedVolumeFromManagedSnapshot(cmd.getOptions2(), cmd.getOptions())) {
            return createManagedVolumeFromManagedSnapshot(cmd);
        }

        if (isCreateNonManagedVolumeFromManagedSnapshot(cmd.getOptions2(), cmd.getOptions())) {
            return createNonManagedVolumeFromManagedSnapshot(cmd);
        }

        if (!(imageStore instanceof NfsTO)) {
            return new CopyCmdAnswer("unsupported protocol");
        }

        final NfsTO nfsImageStore = (NfsTO)imageStore;
        final String primaryStorageNameLabel = pool.getUuid();
        final String secondaryStorageUrl = nfsImageStore.getUrl();
        final int wait = cmd.getWait();
        boolean result = false;
        // Generic error message.
        String details = null;
        String volumeUUID = null;

        if (secondaryStorageUrl == null) {
            details += " because the URL passed: " + secondaryStorageUrl + " is invalid.";
            return new CopyCmdAnswer(details);
        }
        SR srcSr = null;
        VDI destVdi = null;

        SR primaryStorageSR = null;

        try {
            if (pool.isManaged()) {
                Map<String, String> destDetails = cmd.getOptions2();

                final String iScsiName = destDetails.get(DiskTO.IQN);
                final String storageHost = destDetails.get(DiskTO.STORAGE_HOST);
                final String chapInitiatorUsername = destDetails.get(DiskTO.CHAP_INITIATOR_USERNAME);
                final String chapInitiatorSecret = destDetails.get(DiskTO.CHAP_INITIATOR_SECRET);
                final String srType = CitrixResourceBase.SRType.LVMOISCSI.toString();

                primaryStorageSR = hypervisorResource.getIscsiSR(conn, iScsiName, storageHost, iScsiName, chapInitiatorUsername, chapInitiatorSecret, false, srType, true);

            } else {
                primaryStorageSR = hypervisorResource.getSRByNameLabelandHost(conn, primaryStorageNameLabel);
            }

            if (primaryStorageSR == null) {
                throw new InternalErrorException("Could not create volume from snapshot because the primary Storage SR could not be created from the name label: " + primaryStorageNameLabel);
            }

            final String nameLabel = "cloud-" + UUID.randomUUID().toString();
            destVdi = createVdi(conn, nameLabel, primaryStorageSR, volume.getSize());
            volumeUUID = destVdi.getUuid(conn);
            final String snapshotInstallPath = snapshot.getPath();
            final int index = snapshotInstallPath.lastIndexOf(File.separator);
            final String snapshotDirectory = snapshotInstallPath.substring(0, index);
            final String snapshotUuid = getSnapshotUuid(snapshotInstallPath);

            final URI uri = new URI(secondaryStorageUrl);
            srcSr = createFileSr(conn, uri.getHost() + ":" + uri.getPath(), snapshotDirectory);

            final String[] parents = snapshot.getParents();
            final List<VDI> snapshotChains = new ArrayList<VDI>();
            if (parents != null) {
                for (int i = 0; i < parents.length; i++) {
                    final String snChainPath = parents[i];
                    final String uuid = getSnapshotUuid(snChainPath);
                    final VDI chain = VDI.getByUuid(conn, uuid);
                    snapshotChains.add(chain);
                }
            }

            final VDI snapshotVdi = VDI.getByUuid(conn, snapshotUuid);
            snapshotChains.add(snapshotVdi);

            for (final VDI snapChain : snapshotChains) {
                final Task task = snapChain.copyAsync(conn, null, null, destVdi);
                // poll every 1 seconds ,
                hypervisorResource.waitForTask(conn, task, 1000, wait * 1000);
                hypervisorResource.checkForSuccess(conn, task);
                task.destroy(conn);
            }

            result = true;
            destVdi = VDI.getByUuid(conn, volumeUUID);
            final VDI.Record vdir = destVdi.getRecord(conn);
            final VolumeObjectTO newVol = new VolumeObjectTO();
            newVol.setPath(volumeUUID);
            newVol.setSize(vdir.virtualSize);

            return new CopyCmdAnswer(newVol);
        } catch (final Types.XenAPIException e) {
            details += " due to " + e.toString();
            s_logger.warn(details, e);
        } catch (final Exception e) {
            details += " due to " + e.getMessage();
            s_logger.warn(details, e);
        } finally {
            if (srcSr != null) {
                hypervisorResource.skipOrRemoveSR(conn, srcSr);
            }

            if (pool.isManaged()) {
                hypervisorResource.removeSR(conn, primaryStorageSR);
            }

            if (!result && destVdi != null) {
                try {
                    destVdi.destroy(conn);
                } catch (final Exception e) {
                    s_logger.debug("destroy dest vdi failed", e);
                }
            }
        }
        if (!result) {
            // Is this logged at a higher level?
            s_logger.error(details);
        }

        // In all cases return something.
        return new CopyCmdAnswer(details);
    }

};