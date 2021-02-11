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
        final String primaryStorageNameLabel = srcData.getDataStore().getUuid();
        String secondaryStorageUrl = null;
        NfsTO cacheStore = null;
        String destPath = null;
        if (cacheData != null) {
            cacheStore = (NfsTO) cacheData.getDataStore();
            secondaryStorageUrl = cacheStore.getUrl();
            destPath = cacheData.getPath();
        } else {
            cacheStore = (NfsTO) destData.getDataStore();
            secondaryStorageUrl = cacheStore.getUrl();
            destPath = destData.getPath();
        }

        final SnapshotObjectTO snapshotTO = (SnapshotObjectTO) srcData;
        final SnapshotObjectTO snapshotOnImage = (SnapshotObjectTO) destData;
        final String snapshotUuid = snapshotTO.getPath();
        final String volumeUuid = snapshotTO.getVolume().getPath();

        final String prevBackupUuid = snapshotOnImage.getParentSnapshotPath();
        final String prevSnapshotUuid = snapshotTO.getParentSnapshotPath();

        // By default assume failure
        String details = null;
        String snapshotBackupUuid = null;
        Long physicalSize = null;
        final Map<String, String> options = cmd.getOptions();
        boolean fullbackup = Boolean.parseBoolean(options.get("fullSnapshot"));
        boolean result = false;
        try {
            final SR primaryStorageSR = hypervisorResource.getSRByNameLabelandHost(conn, primaryStorageNameLabel);
            if (primaryStorageSR == null) {
                throw new InternalErrorException("Could not backup snapshot because the primary Storage SR could not be created from the name label: " +
                        primaryStorageNameLabel);
            }
            final String psUuid = primaryStorageSR.getUuid(conn);
            final Boolean isISCSI = IsISCSI(primaryStorageSR.getType(conn));

            final VDI snapshotVdi = getVDIbyUuid(conn, snapshotUuid);
            String snapshotPaUuid = null;

            if (prevSnapshotUuid != null && !fullbackup) {
                try {
                    snapshotPaUuid = getVhdParent(conn, psUuid, snapshotUuid, isISCSI);
                    if (snapshotPaUuid != null) {
                        final String snashotPaPaPaUuid = getVhdParent(conn, psUuid, snapshotPaUuid, isISCSI);
                        final String prevSnashotPaUuid = getVhdParent(conn, psUuid, prevSnapshotUuid, isISCSI);
                        if (snashotPaPaPaUuid != null && prevSnashotPaUuid != null && prevSnashotPaUuid.equals(snashotPaPaPaUuid)) {
                            fullbackup = false;
                        } else {
                            fullbackup = true;
                        }
                    }
                } catch (final Exception e) {
                    s_logger.debug("Failed to get parent snapshots, take full snapshot", e);
                    fullbackup = true;
                }
            }

            final URI uri = new URI(secondaryStorageUrl);
            final String secondaryStorageMountPath = uri.getHost() + ":" + uri.getPath();
            final DataStoreTO destStore = destData.getDataStore();
            final String folder = destPath;
            String finalPath = null;

            final String localMountPoint = BaseMountPointOnHost + File.separator + UUID.nameUUIDFromBytes(secondaryStorageUrl.getBytes()).toString();
            if (fullbackup) {
                // the first snapshot is always a full snapshot

                if (!hypervisorResource.createSecondaryStorageFolder(conn, secondaryStorageMountPath, folder)) {
                    details = " Filed to create folder " + folder + " in secondary storage";
                    s_logger.warn(details);
                    return new CopyCmdAnswer(details);
                }
                final String snapshotMountpoint = secondaryStorageUrl + "/" + folder;
                SR snapshotSr = null;
                try {
                    snapshotSr = hypervisorResource.createNfsSRbyURI(conn, new URI(snapshotMountpoint), false);
                    final VDI backedVdi = hypervisorResource.cloudVDIcopy(conn, snapshotVdi, snapshotSr, wait);
                    snapshotBackupUuid = backedVdi.getUuid(conn);
                    final String primarySRuuid = snapshotSr.getUuid(conn);
                    physicalSize = getSnapshotSize(conn, primarySRuuid, snapshotBackupUuid, isISCSI, wait);

                    if (destStore instanceof SwiftTO) {
                        try {
                            final String container = "S-" + snapshotTO.getVolume().getVolumeId().toString();
                            final String destSnapshotName = swiftBackupSnapshot(conn, (SwiftTO) destStore, snapshotSr.getUuid(conn), snapshotBackupUuid, container, false, wait);
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
                            finalPath = backupSnapshotToS3(conn, (S3TO) destStore, snapshotSr.getUuid(conn), folder, snapshotBackupUuid, isISCSI, wait);
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
                        // finalPath = folder + File.separator + snapshotBackupUuid;
                    } else {
                        finalPath = folder + cacheStore.getPathSeparator() + snapshotBackupUuid;
                    }

                } finally {
                    if (snapshotSr != null) {
                        hypervisorResource.removeSR(conn, snapshotSr);
                    }
                }
            } else {
                final String primaryStorageSRUuid = primaryStorageSR.getUuid(conn);
                if (destStore instanceof SwiftTO) {
                    final String container = "S-" + snapshotTO.getVolume().getVolumeId().toString();
                    snapshotBackupUuid =
                            swiftBackupSnapshot(conn, (SwiftTO) destStore, primaryStorageSRUuid, snapshotPaUuid, "S-" + snapshotTO.getVolume().getVolumeId().toString(),
                                    isISCSI, wait);
                    finalPath = container + File.separator + snapshotBackupUuid;
                } else if (destStore instanceof S3TO) {
                    finalPath = backupSnapshotToS3(conn, (S3TO) destStore, primaryStorageSRUuid, folder, snapshotPaUuid, isISCSI, wait);
                    if (finalPath == null) {
                        throw new CloudRuntimeException("S3 upload of snapshots " + snapshotPaUuid + " failed");
                    }
                } else {
                    final String results =
                            backupSnapshot(conn, primaryStorageSRUuid, localMountPoint, folder, secondaryStorageMountPath, snapshotUuid, prevBackupUuid, isISCSI, wait);

                    final String[] tmp = results.split("#");
                    snapshotBackupUuid = tmp[1];
                    physicalSize = Long.parseLong(tmp[2]);
                    finalPath = folder + cacheStore.getPathSeparator() + snapshotBackupUuid;
                }
            }
            // delete primary snapshots with only the last one left
            destroySnapshotOnPrimaryStorageExceptThis(conn, volumeUuid, snapshotUuid);

            final SnapshotObjectTO newSnapshot = new SnapshotObjectTO();
            newSnapshot.setPath(finalPath);
            newSnapshot.setPhysicalSize(physicalSize);
            if (fullbackup) {
                newSnapshot.setParentSnapshotPath(null);
            } else {
                newSnapshot.setParentSnapshotPath(prevBackupUuid);
            }
            result = true;
            return new CopyCmdAnswer(newSnapshot);
        } catch (final XenAPIException e) {
            details = "BackupSnapshot Failed due to " + e.toString();
            s_logger.warn(details, e);
        } catch (final Exception e) {
            details = "BackupSnapshot Failed due to " + e.getMessage();
            s_logger.warn(details, e);
        } finally {
            if (!result) {
                // remove last bad primary snapshot when exception happens
                try {
                    destroySnapshotOnPrimaryStorage(conn, snapshotUuid);
                } catch (final Exception e) {
                    s_logger.debug("clean up snapshot failed", e);
                }
            }
        }

        return new CopyCmdAnswer(details);
    }

};