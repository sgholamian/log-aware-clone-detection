//,temp,StorageSystemDataMotionStrategy.java,2275,2379,temp,StorageSystemDataMotionStrategy.java,1079,1199
//,3
public class xxx {
    private void handleCreateTemplateFromManagedVolume(VolumeInfo volumeInfo, TemplateInfo templateInfo, AsyncCompletionCallback<CopyCommandResult> callback) {
        boolean srcVolumeDetached = volumeInfo.getAttachedVM() == null;

        String errMsg = null;
        CopyCmdAnswer copyCmdAnswer = null;

        try {
            if (!ImageFormat.QCOW2.equals(volumeInfo.getFormat())) {
                throw new CloudRuntimeException("When using managed storage, you can only create a template from a volume on KVM currently.");
            }

            volumeInfo.processEvent(Event.MigrationRequested);

            HostVO hostVO = getHost(volumeInfo.getDataCenterId(), HypervisorType.KVM, false);
            DataStore srcDataStore = volumeInfo.getDataStore();

            int primaryStorageDownloadWait = StorageManager.PRIMARY_STORAGE_DOWNLOAD_WAIT.value();

            CopyCommand copyCommand = new CopyCommand(volumeInfo.getTO(), templateInfo.getTO(), primaryStorageDownloadWait, VirtualMachineManager.ExecuteInSequence.value());

            try {
                handleQualityOfServiceForVolumeMigration(volumeInfo, PrimaryDataStoreDriver.QualityOfServiceState.MIGRATION);

                if (srcVolumeDetached) {
                    _volumeService.grantAccess(volumeInfo, hostVO, srcDataStore);
                }

                Map<String, String> srcDetails = getVolumeDetails(volumeInfo);

                copyCommand.setOptions(srcDetails);

                copyCmdAnswer = (CopyCmdAnswer)agentManager.send(hostVO.getId(), copyCommand);

                if (!copyCmdAnswer.getResult()) {
                    errMsg = copyCmdAnswer.getDetails();

                    LOGGER.warn(errMsg);

                    throw new CloudRuntimeException(errMsg);
                }

                VMTemplateVO vmTemplateVO = _vmTemplateDao.findById(templateInfo.getId());

                vmTemplateVO.setHypervisorType(HypervisorType.KVM);

                _vmTemplateDao.update(vmTemplateVO.getId(), vmTemplateVO);
            }
            catch (CloudRuntimeException | AgentUnavailableException | OperationTimedoutException ex) {
                String msg = "Failed to create template from volume (Volume ID = " + volumeInfo.getId() + ") : ";

                LOGGER.warn(msg, ex);

                throw new CloudRuntimeException(msg + ex.getMessage(), ex);
            }
            finally {
                if (srcVolumeDetached) {
                    try {
                        _volumeService.revokeAccess(volumeInfo, hostVO, srcDataStore);
                    }
                    catch (Exception ex) {
                        LOGGER.warn("Error revoking access to volume (Volume ID = " + volumeInfo.getId() + "): " + ex.getMessage(), ex);
                    }
                }

                handleQualityOfServiceForVolumeMigration(volumeInfo, PrimaryDataStoreDriver.QualityOfServiceState.NO_MIGRATION);

                if (copyCmdAnswer == null || !copyCmdAnswer.getResult()) {
                    if (copyCmdAnswer != null && !StringUtils.isEmpty(copyCmdAnswer.getDetails())) {
                        errMsg = copyCmdAnswer.getDetails();
                    }
                    else {
                        errMsg = "Unable to create template from volume";
                    }
                }

                try {
                    if (StringUtils.isEmpty(errMsg)) {
                        volumeInfo.processEvent(Event.OperationSuccessed);
                    }
                    else {
                        volumeInfo.processEvent(Event.OperationFailed);
                    }
                }
                catch (Exception ex) {
                    LOGGER.warn("Error processing snapshot event: " + ex.getMessage(), ex);
                }
            }
        }
        catch (Exception ex) {
            errMsg = ex.getMessage();

            throw new CloudRuntimeException(errMsg);
        }
        finally {
            if (copyCmdAnswer == null) {
                copyCmdAnswer = new CopyCmdAnswer(errMsg);
            }

            CopyCommandResult result = new CopyCommandResult(null, copyCmdAnswer);

            result.setResult(errMsg);

            callback.complete(result);
        }
    }

};