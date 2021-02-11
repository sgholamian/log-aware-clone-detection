//,temp,StorageSystemDataMotionStrategy.java,1436,1567,temp,StorageSystemDataMotionStrategy.java,1302,1434
//,3
public class xxx {
    private void handleCreateManagedVolumeFromManagedSnapshot(SnapshotInfo snapshotInfo, VolumeInfo volumeInfo,
                                                              AsyncCompletionCallback<CopyCommandResult> callback) {
        String errMsg = null;
        CopyCmdAnswer copyCmdAnswer = null;

        boolean useCloning = true;

        try {
            verifyFormat(snapshotInfo);

            HostVO hostVO = getHost(snapshotInfo);

            boolean usingBackendSnapshot = usingBackendSnapshotFor(snapshotInfo);
            boolean computeClusterSupportsVolumeClone = true;

            if (HypervisorType.XenServer.equals(snapshotInfo.getHypervisorType())) {
                computeClusterSupportsVolumeClone = clusterDao.getSupportsResigning(hostVO.getClusterId());

                if (usingBackendSnapshot && !computeClusterSupportsVolumeClone) {
                    String noSupportForResignErrMsg = "Unable to locate an applicable host with which to perform a resignature operation : Cluster ID = " +
                            hostVO.getClusterId();

                    LOGGER.warn(noSupportForResignErrMsg);

                    throw new CloudRuntimeException(noSupportForResignErrMsg);
                }
            }

            boolean canStorageSystemCreateVolumeFromVolume = canStorageSystemCreateVolumeFromVolume(snapshotInfo.getDataStore().getId());

            useCloning = usingBackendSnapshot || (canStorageSystemCreateVolumeFromVolume && computeClusterSupportsVolumeClone);

            VolumeDetailVO volumeDetail = null;

            if (useCloning) {
                volumeDetail = new VolumeDetailVO(volumeInfo.getId(),
                    "cloneOfSnapshot",
                    String.valueOf(snapshotInfo.getId()),
                    false);

                volumeDetail = volumeDetailsDao.persist(volumeDetail);
            }

            // at this point, the snapshotInfo and volumeInfo should have the same disk offering ID (so either one should be OK to get a DiskOfferingVO instance)
            DiskOfferingVO diskOffering = _diskOfferingDao.findByIdIncludingRemoved(volumeInfo.getDiskOfferingId());
            SnapshotVO snapshot = _snapshotDao.findById(snapshotInfo.getId());

            // update the volume's hv_ss_reserve (hypervisor snapshot reserve) from a disk offering (used for managed storage)
            _volumeService.updateHypervisorSnapshotReserveForVolume(diskOffering, volumeInfo.getId(), snapshot.getHypervisorType());

            AsyncCallFuture<VolumeApiResult> future = _volumeService.createVolumeAsync(volumeInfo, volumeInfo.getDataStore());
            VolumeApiResult result = future.get();

            if (volumeDetail != null) {
                volumeDetailsDao.remove(volumeDetail.getId());
            }

            if (result.isFailed()) {
                LOGGER.warn("Failed to create a volume: " + result.getResult());

                throw new CloudRuntimeException(result.getResult());
            }

            volumeInfo = _volumeDataFactory.getVolume(volumeInfo.getId(), volumeInfo.getDataStore());
            volumeInfo.processEvent(Event.MigrationRequested);
            volumeInfo = _volumeDataFactory.getVolume(volumeInfo.getId(), volumeInfo.getDataStore());

            if (HypervisorType.XenServer.equals(snapshotInfo.getHypervisorType()) || HypervisorType.VMware.equals(snapshotInfo.getHypervisorType())) {
                if (useCloning) {
                    Map<String, String> extraDetails = null;

                    if (HypervisorType.VMware.equals(snapshotInfo.getHypervisorType())) {
                        extraDetails = new HashMap<>();

                        String extraDetailsVmdk = getSnapshotProperty(snapshotInfo.getId(), DiskTO.VMDK);

                        extraDetails.put(DiskTO.VMDK, extraDetailsVmdk);
                    }

                    copyCmdAnswer = performResignature(volumeInfo, hostVO, extraDetails);

                    // If using VMware, have the host rescan its software HBA if dynamic discovery is in use.
                    if (HypervisorType.VMware.equals(snapshotInfo.getHypervisorType())) {
                        disconnectHostFromVolume(hostVO, volumeInfo.getPoolId(), volumeInfo.get_iScsiName());
                    }
                } else {
                    // asking for a XenServer host here so we don't always prefer to use XenServer hosts that support resigning
                    // even when we don't need those hosts to do this kind of copy work
                    hostVO = getHost(snapshotInfo.getDataCenterId(), snapshotInfo.getHypervisorType(), false);

                    handleQualityOfServiceForVolumeMigration(volumeInfo, PrimaryDataStoreDriver.QualityOfServiceState.MIGRATION);

                    copyCmdAnswer = performCopyOfVdi(volumeInfo, snapshotInfo, hostVO);
                }

                verifyCopyCmdAnswer(copyCmdAnswer, snapshotInfo);
            }
            else if (HypervisorType.KVM.equals(snapshotInfo.getHypervisorType())) {
                VolumeObjectTO newVolume = new VolumeObjectTO();

                newVolume.setSize(volumeInfo.getSize());
                newVolume.setPath(volumeInfo.get_iScsiName());
                newVolume.setFormat(volumeInfo.getFormat());

                copyCmdAnswer = new CopyCmdAnswer(newVolume);
            }
            else {
                throw new CloudRuntimeException("Unsupported hypervisor type");
            }
        }
        catch (Exception ex) {
            errMsg = "Copy operation failed in 'StorageSystemDataMotionStrategy.handleCreateManagedVolumeFromManagedSnapshot': " +
                    ex.getMessage();

            throw new CloudRuntimeException(errMsg);
        }
        finally {
            if (useCloning) {
                handleQualityOfServiceForVolumeMigration(volumeInfo, PrimaryDataStoreDriver.QualityOfServiceState.NO_MIGRATION);
            }

            if (copyCmdAnswer == null) {
                copyCmdAnswer = new CopyCmdAnswer(errMsg);
            }

            CopyCommandResult result = new CopyCommandResult(null, copyCmdAnswer);

            result.setResult(errMsg);

            callback.complete(result);
        }
    }

};