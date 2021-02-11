//,temp,ImageStoreUploadMonitorImpl.java,387,483,temp,ImageStoreUploadMonitorImpl.java,301,380
//,3
public class xxx {
                @Override
                public void doInTransactionWithoutResult(TransactionStatus status) {
                    VolumeVO tmpVolume = _volumeDao.findById(volume.getId());
                    VolumeDataStoreVO tmpVolumeDataStore = _volumeDataStoreDao.findById(volumeDataStore.getId());
                    boolean sendAlert = false;
                    String msg = null;
                    try {
                        switch (answer.getStatus()) {
                        case COMPLETED:
                            tmpVolumeDataStore.setDownloadState(VMTemplateStorageResourceAssoc.Status.DOWNLOADED);
                            tmpVolumeDataStore.setState(State.Ready);
                            tmpVolumeDataStore.setInstallPath(answer.getInstallPath());
                            tmpVolumeDataStore.setPhysicalSize(answer.getPhysicalSize());
                            tmpVolumeDataStore.setSize(answer.getVirtualSize());
                            tmpVolumeDataStore.setDownloadPercent(100);

                            VolumeVO volumeUpdate = _volumeDao.createForUpdate();
                            volumeUpdate.setSize(answer.getVirtualSize());
                            _volumeDao.update(tmpVolume.getId(), volumeUpdate);
                            stateMachine.transitTo(tmpVolume, Event.OperationSucceeded, null, _volumeDao);
                            _resourceLimitMgr.incrementResourceCount(volume.getAccountId(), Resource.ResourceType.secondary_storage, answer.getVirtualSize());

                            // publish usage events
                            UsageEventUtils.publishUsageEvent(EventTypes.EVENT_VOLUME_UPLOAD, tmpVolume.getAccountId(),
                                    tmpVolumeDataStore.getDataStoreId(), tmpVolume.getId(), tmpVolume.getName(),
                                    null, null, tmpVolumeDataStore.getPhysicalSize(), tmpVolumeDataStore.getSize(),
                                    Volume.class.getName(), tmpVolume.getUuid());

                            if (s_logger.isDebugEnabled()) {
                                s_logger.debug("Volume " + tmpVolume.getUuid() + " uploaded successfully");
                            }
                            break;
                        case IN_PROGRESS:
                            if (tmpVolume.getState() == Volume.State.NotUploaded) {
                                tmpVolumeDataStore.setDownloadState(VMTemplateStorageResourceAssoc.Status.DOWNLOAD_IN_PROGRESS);
                                tmpVolumeDataStore.setDownloadPercent(answer.getDownloadPercent());
                                stateMachine.transitTo(tmpVolume, Event.UploadRequested, null, _volumeDao);
                            } else if (tmpVolume.getState() == Volume.State.UploadInProgress) { // check for timeout
                                if (System.currentTimeMillis() - tmpVolumeDataStore.getCreated().getTime() > _uploadOperationTimeout) {
                                    tmpVolumeDataStore.setDownloadState(VMTemplateStorageResourceAssoc.Status.DOWNLOAD_ERROR);
                                    tmpVolumeDataStore.setState(State.Failed);
                                    stateMachine.transitTo(tmpVolume, Event.OperationFailed, null, _volumeDao);
                                    msg = "Volume " + tmpVolume.getUuid() + " failed to upload due to operation timed out";
                                    s_logger.error(msg);
                                    sendAlert = true;
                                } else {
                                    tmpVolumeDataStore.setDownloadPercent(answer.getDownloadPercent());
                                }
                            }
                            break;
                        case ERROR:
                            tmpVolumeDataStore.setDownloadState(VMTemplateStorageResourceAssoc.Status.DOWNLOAD_ERROR);
                            tmpVolumeDataStore.setState(State.Failed);
                            stateMachine.transitTo(tmpVolume, Event.OperationFailed, null, _volumeDao);
                            msg = "Volume " + tmpVolume.getUuid() + " failed to upload. Error details: " + answer.getDetails();
                            s_logger.error(msg);
                            sendAlert = true;
                            break;
                        case UNKNOWN:
                            if (tmpVolume.getState() == Volume.State.NotUploaded) { // check for timeout
                                if (System.currentTimeMillis() - tmpVolumeDataStore.getCreated().getTime() > _uploadOperationTimeout) {
                                    tmpVolumeDataStore.setDownloadState(VMTemplateStorageResourceAssoc.Status.ABANDONED);
                                    tmpVolumeDataStore.setState(State.Failed);
                                    stateMachine.transitTo(tmpVolume, Event.OperationTimeout, null, _volumeDao);
                                    msg = "Volume " + tmpVolume.getUuid() + " failed to upload due to operation timed out";
                                    s_logger.error(msg);
                                    sendAlert = true;
                                }
                            }
                            break;
                        }
                        _volumeDataStoreDao.update(tmpVolumeDataStore.getId(), tmpVolumeDataStore);
                    } catch (NoTransitionException e) {
                        s_logger.error("Unexpected error " + e.getMessage());
                    } finally {
                        if (sendAlert) {
                            _alertMgr.sendAlert(AlertManager.AlertType.ALERT_TYPE_UPLOAD_FAILED, tmpVolume.getDataCenterId(), null, msg, msg);
                        }
                    }
                }

};