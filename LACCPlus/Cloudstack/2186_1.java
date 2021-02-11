//,temp,Xenserver625StorageProcessor.java,486,681,temp,XenServerStorageProcessor.java,1136,1317
//,3
public class xxx {
    @Override
    public Answer backupSnapshot(final CopyCommand cmd) {
        final Connection conn = hypervisorResource.getConnection();
        final DataTO srcData = cmd.getSrcTO();
        final DataTO cacheData = cmd.getCacheTO();
        final DataTO destData = cmd.getDestTO();
        final int wait = cmd.getWait();
        final PrimaryDataStoreTO primaryStore = (PrimaryDataStoreTO)srcData.getDataStore();
        final String primaryStorageNameLabel = primaryStore.getUuid();
        String secondaryStorageUrl = null;
        NfsTO cacheStore = null;
        String destPath = null;
        if (cacheData != null) {
            cacheStore = (NfsTO)cacheData.getDataStore();
            secondaryStorageUrl = cacheStore.getUrl();
            destPath = cacheData.getPath();
        } else {
            cacheStore = (NfsTO)destData.getDataStore();
            secondaryStorageUrl = cacheStore.getUrl();
            destPath = destData.getPath();
        }

        final SnapshotObjectTO snapshotTO = (SnapshotObjectTO)srcData;
        final SnapshotObjectTO snapshotOnImage = (SnapshotObjectTO)destData;
        String snapshotUuid = snapshotTO.getPath();

        final String prevBackupUuid = snapshotOnImage.getParentSnapshotPath();
        final String prevSnapshotUuid = snapshotTO.getParentSnapshotPath();
        final Map<String, String> options = cmd.getOptions();
        // By default assume failure
        String details = null;
        String snapshotBackupUuid = null;
        boolean fullbackup = Boolean.parseBoolean(options.get("fullSnapshot"));
        Long physicalSize = null;
        try {

            SR primaryStorageSR = null;
            if (primaryStore.isManaged()) {
                fullbackup = true; // currently, managed storage only supports full backup

                final Map<String, String> srcDetails = cmd.getOptions();

                final String iScsiName = srcDetails.get(DiskTO.IQN);
                final String storageHost = srcDetails.get(DiskTO.STORAGE_HOST);
                final String chapInitiatorUsername = srcDetails.get(DiskTO.CHAP_INITIATOR_USERNAME);
                final String chapInitiatorSecret = srcDetails.get(DiskTO.CHAP_INITIATOR_SECRET);
                final String srType = CitrixResourceBase.SRType.LVMOISCSI.toString();

                primaryStorageSR = hypervisorResource.getIscsiSR(conn, iScsiName, storageHost, iScsiName, chapInitiatorUsername, chapInitiatorSecret, false, srType, true);

                final VDI srcVdi = primaryStorageSR.getVDIs(conn).iterator().next();
                if (srcVdi == null) {
                    throw new InternalErrorException("Could not Find a VDI on the SR: " + primaryStorageSR.getNameLabel(conn));
                }
                snapshotUuid = srcVdi.getUuid(conn);

            } else {
                primaryStorageSR = hypervisorResource.getSRByNameLabelandHost(conn, primaryStorageNameLabel);
            }

            if (primaryStorageSR == null) {
                throw new InternalErrorException("Could not backup snapshot because the primary Storage SR could not be created from the name label: " + primaryStorageNameLabel);
            }
            // String psUuid = primaryStorageSR.getUuid(conn);
            final Boolean isISCSI = IsISCSI(primaryStorageSR.getType(conn));

            final VDI snapshotVdi = getVDIbyUuid(conn, snapshotUuid);
            final String snapshotPaUuid = snapshotVdi.getUuid(conn);

            final URI uri = new URI(secondaryStorageUrl);
            final String secondaryStorageMountPath = uri.getHost() + ":" + uri.getPath();
            final DataStoreTO destStore = destData.getDataStore();
            final String folder = destPath;
            String finalPath = null;

            final String localMountPoint = BaseMountPointOnHost + File.separator + UUID.nameUUIDFromBytes(secondaryStorageUrl.getBytes()).toString();
            if (fullbackup) {
                SR snapshotSr = null;
                Task task = null;
                try {
                    final String localDir = "/var/cloud_mount/" + UUID.nameUUIDFromBytes(secondaryStorageMountPath.getBytes());
                    mountNfs(conn, secondaryStorageMountPath, localDir);
                    final boolean result = makeDirectory(conn, localDir + "/" + folder);
                    if (!result) {
                        details = " Failed to create folder " + folder + " in secondary storage";
                        s_logger.warn(details);
                        return new CopyCmdAnswer(details);
                    }

                    snapshotSr = createFileSr(conn, secondaryStorageMountPath, folder);

                    task = snapshotVdi.copyAsync(conn, snapshotSr, null, null);
                    // poll every 1 seconds ,
                    hypervisorResource.waitForTask(conn, task, 1000, wait * 1000);
                    hypervisorResource.checkForSuccess(conn, task);
                    final VDI backedVdi = Types.toVDI(task, conn);
                    snapshotBackupUuid = backedVdi.getUuid(conn);
                    snapshotSr.scan(conn);
                    physicalSize = backedVdi.getPhysicalUtilisation(conn);

                    if (destStore instanceof SwiftTO) {
                        try {
                            final String container = "S-" + snapshotTO.getVolume().getVolumeId().toString();
                            final String destSnapshotName = swiftBackupSnapshot(conn, (SwiftTO)destStore, snapshotSr.getUuid(conn), snapshotBackupUuid, container, false, wait);
                            final String swiftPath = container + File.separator + destSnapshotName;
                            finalPath = swiftPath;
                        } finally {
                            try {
                                deleteSnapshotBackup(conn, localMountPoint, folder, secondaryStorageMountPath, snapshotBackupUuid);
                            } catch (final Exception e) {
                                s_logger.debug("Failed to delete snapshot on cache storages", e);
                            }
                        }

                    } else if (destStore instanceof S3TO) {
                        try {
                            finalPath = backupSnapshotToS3(conn, (S3TO)destStore, snapshotSr.getUuid(conn), folder, snapshotBackupUuid, isISCSI, wait);
                            if (finalPath == null) {
                                throw new CloudRuntimeException("S3 upload of snapshots " + snapshotBackupUuid + " failed");
                            }
                        } finally {
                            try {
                                deleteSnapshotBackup(conn, localMountPoint, folder, secondaryStorageMountPath, snapshotBackupUuid);
                            } catch (final Exception e) {
                                s_logger.debug("Failed to delete snapshot on cache storages", e);
                            }
                        }
                        // finalPath = folder + File.separator +
                        // snapshotBackupUuid;
                    } else {
                        finalPath = folder + File.separator + snapshotBackupUuid + ".vhd";
                    }

                } finally {
                    if (task != null) {
                        try {
                            task.destroy(conn);
                        } catch (final Exception e) {
                            s_logger.warn("unable to destroy task(" + task.toWireString() + ") due to " + e.toString());
                        }
                    }
                    if (snapshotSr != null) {
                        hypervisorResource.removeSR(conn, snapshotSr);
                    }

                    if (primaryStore.isManaged()) {
                        hypervisorResource.removeSR(conn, primaryStorageSR);
                    }
                }
            } else {
                final String primaryStorageSRUuid = primaryStorageSR.getUuid(conn);
                if (destStore instanceof SwiftTO) {
                    final String container = "S-" + snapshotTO.getVolume().getVolumeId().toString();
                    snapshotBackupUuid = swiftBackupSnapshot(conn, (SwiftTO)destStore, primaryStorageSRUuid, snapshotPaUuid, "S-" + snapshotTO.getVolume().getVolumeId().toString(), isISCSI, wait);
                    finalPath = container + File.separator + snapshotBackupUuid;
                } else if (destStore instanceof S3TO) {
                    finalPath = backupSnapshotToS3(conn, (S3TO)destStore, primaryStorageSRUuid, folder, snapshotPaUuid, isISCSI, wait);
                    if (finalPath == null) {
                        throw new CloudRuntimeException("S3 upload of snapshots " + snapshotPaUuid + " failed");
                    }
                } else {
                    final String result = backupSnapshot(conn, primaryStorageSRUuid, localMountPoint, folder, secondaryStorageMountPath, snapshotUuid, prevBackupUuid, prevSnapshotUuid, isISCSI, wait);
                    final String[] tmp = result.split("#");
                    snapshotBackupUuid = tmp[0];
                    physicalSize = Long.parseLong(tmp[1]);
                    finalPath = folder + File.separator + snapshotBackupUuid + ".vhd";
                }
            }

            // remove every snapshot except this one from primary storage
            final String volumeUuid = snapshotTO.getVolume().getPath();
            destroySnapshotOnPrimaryStorageExceptThis(conn, volumeUuid, snapshotUuid);

            final SnapshotObjectTO newSnapshot = new SnapshotObjectTO();
            newSnapshot.setPath(finalPath);
            newSnapshot.setPhysicalSize(physicalSize);
            if (fullbackup) {
                newSnapshot.setParentSnapshotPath(null);
            } else {
                newSnapshot.setParentSnapshotPath(prevBackupUuid);
            }
            s_logger.info("New snapshot details: " + newSnapshot.toString());
            s_logger.info("New snapshot physical utilization: " + physicalSize);

            return new CopyCmdAnswer(newSnapshot);
        } catch (final Exception e) {
            final String reason = e instanceof Types.XenAPIException ? e.toString() : e.getMessage();
            details = "BackupSnapshot Failed due to " + reason;
            s_logger.warn(details, e);

            // remove last bad primary snapshot when exception happens
            destroySnapshotOnPrimaryStorage(conn, snapshotUuid);
        }

        return new CopyCmdAnswer(details);
    }

};