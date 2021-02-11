//,temp,StorageSystemDataMotionStrategy.java,2275,2379,temp,StorageSystemDataMotionStrategy.java,1079,1199
//,3
public class xxx {
    private void handleCreateNonManagedVolumeFromManagedSnapshot(SnapshotInfo snapshotInfo, VolumeInfo volumeInfo,
                                                                 AsyncCompletionCallback<CopyCommandResult> callback) {
        if (!HypervisorType.XenServer.equals(snapshotInfo.getHypervisorType())) {
            String errMsg = "Creating a volume on non-managed storage from a snapshot on managed storage is currently only supported with XenServer.";

            handleError(errMsg, callback);
        }

        long volumeStoragePoolId = volumeInfo.getDataStore().getId();
        StoragePoolVO volumeStoragePoolVO = _storagePoolDao.findById(volumeStoragePoolId);

        if (volumeStoragePoolVO.getClusterId() == null) {
            String errMsg = "To create a non-managed volume from a managed snapshot, the destination storage pool must be cluster scoped.";

            handleError(errMsg, callback);
        }

        String errMsg = null;
        CopyCmdAnswer copyCmdAnswer = null;

        boolean usingBackendSnapshot = false;

        try {
            snapshotInfo.processEvent(Event.CopyingRequested);

            usingBackendSnapshot = usingBackendSnapshotFor(snapshotInfo);

            if (usingBackendSnapshot) {
                boolean computeClusterSupportsVolumeClone = clusterDao.getSupportsResigning(volumeStoragePoolVO.getClusterId());

                if (!computeClusterSupportsVolumeClone) {
                    String noSupportForResignErrMsg = "Unable to locate an applicable host with which to perform a resignature operation : Cluster ID = " +
                            volumeStoragePoolVO.getClusterId();

                    LOGGER.warn(noSupportForResignErrMsg);

                    throw new CloudRuntimeException(noSupportForResignErrMsg);
                }

                createVolumeFromSnapshot(snapshotInfo);

                HostVO hostVO = getHost(snapshotInfo.getDataCenterId(), HypervisorType.XenServer, true);

                copyCmdAnswer = performResignature(snapshotInfo, hostVO, null, true);

                verifyCopyCmdAnswer(copyCmdAnswer, snapshotInfo);
            }

            int primaryStorageDownloadWait = StorageManager.PRIMARY_STORAGE_DOWNLOAD_WAIT.value();

            CopyCommand copyCommand = new CopyCommand(snapshotInfo.getTO(), volumeInfo.getTO(), primaryStorageDownloadWait,
                    VirtualMachineManager.ExecuteInSequence.value());

            HostVO hostVO = getHostInCluster(volumeStoragePoolVO.getClusterId());

            if (!usingBackendSnapshot) {
                long snapshotStoragePoolId = snapshotInfo.getDataStore().getId();
                DataStore snapshotDataStore = dataStoreMgr.getDataStore(snapshotStoragePoolId, DataStoreRole.Primary);

                _volumeService.grantAccess(snapshotInfo, hostVO, snapshotDataStore);
            }

            Map<String, String> srcDetails = getSnapshotDetails(snapshotInfo);

            copyCommand.setOptions(srcDetails);

            copyCmdAnswer = (CopyCmdAnswer)agentManager.send(hostVO.getId(), copyCommand);

            if (!copyCmdAnswer.getResult()) {
                errMsg = copyCmdAnswer.getDetails();

                LOGGER.warn(errMsg);

                throw new CloudRuntimeException(errMsg);
            }
        }
        catch (Exception ex) {
            errMsg = "Copy operation failed in 'StorageSystemDataMotionStrategy.handleCreateNonManagedVolumeFromManagedSnapshot': " + ex.getMessage();

            throw new CloudRuntimeException(errMsg);
        }
        finally {
            try {
                HostVO hostVO = getHostInCluster(volumeStoragePoolVO.getClusterId());

                long snapshotStoragePoolId = snapshotInfo.getDataStore().getId();
                DataStore snapshotDataStore = dataStoreMgr.getDataStore(snapshotStoragePoolId, DataStoreRole.Primary);

                _volumeService.revokeAccess(snapshotInfo, hostVO, snapshotDataStore);
            }
            catch (Exception e) {
                LOGGER.debug("Failed to revoke access from dest volume", e);
            }

            if (usingBackendSnapshot) {
                deleteVolumeFromSnapshot(snapshotInfo);
            }

            try {
                if (StringUtils.isEmpty(errMsg)) {
                    snapshotInfo.processEvent(Event.OperationSuccessed);
                }
                else {
                    snapshotInfo.processEvent(Event.OperationFailed);
                }
            }
            catch (Exception ex) {
                LOGGER.warn("Error processing snapshot event: " + ex.getMessage(), ex);
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