//,temp,LibvirtBackupSnapshotCommandWrapper.java,59,209,temp,KVMStorageProcessor.java,862,1036
//,3
public class xxx {
    @Override
    public Answer backupSnapshot(final CopyCommand cmd) {
        final DataTO srcData = cmd.getSrcTO();
        final DataTO destData = cmd.getDestTO();
        final SnapshotObjectTO snapshot = (SnapshotObjectTO)srcData;
        final PrimaryDataStoreTO primaryStore = (PrimaryDataStoreTO)snapshot.getDataStore();
        final SnapshotObjectTO destSnapshot = (SnapshotObjectTO)destData;
        final DataStoreTO imageStore = destData.getDataStore();

        if (!(imageStore instanceof NfsTO)) {
            return backupSnapshotForObjectStore(cmd);
        }
        final NfsTO nfsImageStore = (NfsTO)imageStore;

        final String secondaryStoragePoolUrl = nfsImageStore.getUrl();
        // NOTE: snapshot name is encoded in snapshot path
        final int index = snapshot.getPath().lastIndexOf("/");
        final boolean isCreatedFromVmSnapshot = (index == -1) ? true: false; // -1 means the snapshot is created from existing vm snapshot

        final String snapshotName = snapshot.getPath().substring(index + 1);
        String descName = snapshotName;
        final String volumePath = snapshot.getVolume().getPath();
        String snapshotDestPath = null;
        String snapshotRelPath = null;
        final String vmName = snapshot.getVmName();
        KVMStoragePool secondaryStoragePool = null;
        Connect conn = null;
        KVMPhysicalDisk snapshotDisk = null;
        KVMStoragePool primaryPool = null;
        try {
            conn = LibvirtConnection.getConnectionByVmName(vmName);

            secondaryStoragePool = storagePoolMgr.getStoragePoolByURI(secondaryStoragePoolUrl);

            final String ssPmountPath = secondaryStoragePool.getLocalPath();
            snapshotRelPath = destSnapshot.getPath();

            snapshotDestPath = ssPmountPath + File.separator + snapshotRelPath;
            snapshotDisk = storagePoolMgr.getPhysicalDisk(primaryStore.getPoolType(), primaryStore.getUuid(), volumePath);
            primaryPool = snapshotDisk.getPool();

            long size = 0;
            /**
             * Since Ceph version Dumpling (0.67.X) librbd / Qemu supports converting RBD
             * snapshots to RAW/QCOW2 files directly.
             *
             * This reduces the amount of time and storage it takes to back up a snapshot dramatically
             */
            if (primaryPool.getType() == StoragePoolType.RBD) {
                final String rbdSnapshot = snapshotDisk.getPath() +  "@" + snapshotName;
                final String snapshotFile = snapshotDestPath + "/" + snapshotName;
                try {
                    s_logger.debug("Attempting to backup RBD snapshot " + rbdSnapshot);

                    final File snapDir = new File(snapshotDestPath);
                    s_logger.debug("Attempting to create " + snapDir.getAbsolutePath() + " recursively for snapshot storage");
                    FileUtils.forceMkdir(snapDir);

                    final QemuImgFile srcFile =
                            new QemuImgFile(KVMPhysicalDisk.RBDStringBuilder(primaryPool.getSourceHost(), primaryPool.getSourcePort(), primaryPool.getAuthUserName(),
                                    primaryPool.getAuthSecret(), rbdSnapshot));
                    srcFile.setFormat(snapshotDisk.getFormat());

                    final QemuImgFile destFile = new QemuImgFile(snapshotFile);
                    destFile.setFormat(PhysicalDiskFormat.QCOW2);

                    s_logger.debug("Backing up RBD snapshot " + rbdSnapshot + " to " + snapshotFile);
                    final QemuImg q = new QemuImg(cmd.getWaitInMillSeconds());
                    q.convert(srcFile, destFile);

                    final File snapFile = new File(snapshotFile);
                    if(snapFile.exists()) {
                        size = snapFile.length();
                    }

                    s_logger.debug("Finished backing up RBD snapshot " + rbdSnapshot + " to " + snapshotFile + " Snapshot size: " + size);
                } catch (final FileNotFoundException e) {
                    s_logger.error("Failed to open " + snapshotDestPath + ". The error was: " + e.getMessage());
                    return new CopyCmdAnswer(e.toString());
                } catch (final IOException e) {
                    s_logger.error("Failed to create " + snapshotDestPath + ". The error was: " + e.getMessage());
                    return new CopyCmdAnswer(e.toString());
                }  catch (final QemuImgException e) {
                    s_logger.error("Failed to backup the RBD snapshot from " + rbdSnapshot +
                            " to " + snapshotFile + " the error was: " + e.getMessage());
                    return new CopyCmdAnswer(e.toString());
                }
            } else {
                final Script command = new Script(_manageSnapshotPath, cmd.getWaitInMillSeconds(), s_logger);
                command.add("-b", snapshotDisk.getPath());
                command.add("-n", snapshotName);
                command.add("-p", snapshotDestPath);
                if (isCreatedFromVmSnapshot) {
                    descName = UUID.randomUUID().toString();
                }
                command.add("-t", descName);
                final String result = command.execute();
                if (result != null) {
                    s_logger.debug("Failed to backup snaptshot: " + result);
                    return new CopyCmdAnswer(result);
                }
                final File snapFile = new File(snapshotDestPath + "/" + descName);
                if(snapFile.exists()){
                    size = snapFile.length();
                }
            }

            final SnapshotObjectTO newSnapshot = new SnapshotObjectTO();
            newSnapshot.setPath(snapshotRelPath + File.separator + descName);
            newSnapshot.setPhysicalSize(size);
            return new CopyCmdAnswer(newSnapshot);
        } catch (final LibvirtException e) {
            s_logger.debug("Failed to backup snapshot: ", e);
            return new CopyCmdAnswer(e.toString());
        } catch (final CloudRuntimeException e) {
            s_logger.debug("Failed to backup snapshot: ", e);
            return new CopyCmdAnswer(e.toString());
        } finally {
            if (isCreatedFromVmSnapshot) {
                s_logger.debug("Ignoring removal of vm snapshot on primary as this snapshot is created from vm snapshot");
            } else {
                try {
                    /* Delete the snapshot on primary */
                    DomainInfo.DomainState state = null;
                    Domain vm = null;
                    if (vmName != null) {
                        try {
                            vm = resource.getDomain(conn, vmName);
                            state = vm.getInfo().state;
                        } catch (final LibvirtException e) {
                            s_logger.trace("Ignoring libvirt error.", e);
                        }
                    }

                    final KVMStoragePool primaryStorage = storagePoolMgr.getStoragePool(primaryStore.getPoolType(),
                            primaryStore.getUuid());
                    if (state == DomainInfo.DomainState.VIR_DOMAIN_RUNNING && !primaryStorage.isExternalSnapshot()) {
                        final DomainSnapshot snap = vm.snapshotLookupByName(snapshotName);
                        snap.delete(0);

                        /*
                         * libvirt on RHEL6 doesn't handle resume event emitted from
                         * qemu
                         */
                        vm = resource.getDomain(conn, vmName);
                        state = vm.getInfo().state;
                        if (state == DomainInfo.DomainState.VIR_DOMAIN_PAUSED) {
                            vm.resume();
                        }
                    } else {
                        if (primaryPool.getType() != StoragePoolType.RBD) {
                            final Script command = new Script(_manageSnapshotPath, _cmdsTimeout, s_logger);
                            command.add("-d", snapshotDisk.getPath());
                            command.add("-n", snapshotName);
                            final String result = command.execute();
                            if (result != null) {
                                s_logger.debug("Failed to delete snapshot on primary: " + result);
                                // return new CopyCmdAnswer("Failed to backup snapshot: " + result);
                            }
                        }
                    }
                } catch (final Exception ex) {
                    s_logger.debug("Failed to delete snapshots on primary", ex);
                }
            }

            try {
                if (secondaryStoragePool != null) {
                    secondaryStoragePool.delete();
                }
            } catch (final Exception ex) {
                s_logger.debug("Failed to delete secondary storage", ex);
            }
        }
    }

};