//,temp,ImageStoreUploadMonitorImpl.java,387,483,temp,ImageStoreUploadMonitorImpl.java,298,382
//,3
public class xxx {
                @Override
                public void doInTransactionWithoutResult(TransactionStatus status) {
                    VMTemplateVO tmpTemplate = _templateDao.findById(template.getId());
                    TemplateDataStoreVO tmpTemplateDataStore = _templateDataStoreDao.findById(templateDataStore.getId());
                    boolean sendAlert = false;
                    String msg = null;
                    try {
                        switch (answer.getStatus()) {
                        case COMPLETED:
                            tmpTemplateDataStore.setDownloadState(VMTemplateStorageResourceAssoc.Status.DOWNLOADED);
                            tmpTemplateDataStore.setState(State.Ready);
                            tmpTemplateDataStore.setInstallPath(answer.getInstallPath());
                            tmpTemplateDataStore.setPhysicalSize(answer.getPhysicalSize());
                            tmpTemplateDataStore.setSize(answer.getVirtualSize());
                            tmpTemplateDataStore.setDownloadPercent(100);
                            tmpTemplateDataStore.setExtractUrl(null);

                            VMTemplateVO templateUpdate = _templateDao.createForUpdate();
                            templateUpdate.setSize(answer.getVirtualSize());
                            _templateDao.update(tmpTemplate.getId(), templateUpdate);
                            // For multi-disk OVA, check and create data disk templates
                            if (tmpTemplate.getFormat().equals(Storage.ImageFormat.OVA)) {
                                final DataStore store = dataStoreManager.getDataStore(templateDataStore.getDataStoreId(), templateDataStore.getDataStoreRole());
                                final TemplateInfo templateInfo = templateFactory.getTemplate(tmpTemplate.getId(), store);
                                if (!templateService.createOvaDataDiskTemplates(templateInfo)) {
                                    tmpTemplateDataStore.setDownloadState(VMTemplateStorageResourceAssoc.Status.ABANDONED);
                                    tmpTemplateDataStore.setState(State.Failed);
                                    stateMachine.transitTo(tmpTemplate, VirtualMachineTemplate.Event.OperationFailed, null, _templateDao);
                                    msg = "Multi-disk OVA template " + tmpTemplate.getUuid() + " failed to process data disks";
                                    s_logger.error(msg);
                                    sendAlert = true;
                                    break;
                                }
                            }
                            stateMachine.transitTo(tmpTemplate, VirtualMachineTemplate.Event.OperationSucceeded, null, _templateDao);
                            _resourceLimitMgr.incrementResourceCount(template.getAccountId(), Resource.ResourceType.secondary_storage, answer.getVirtualSize());
                            //publish usage event
                            String etype = EventTypes.EVENT_TEMPLATE_CREATE;
                            if (tmpTemplate.getFormat() == Storage.ImageFormat.ISO) {
                                etype = EventTypes.EVENT_ISO_CREATE;
                            }
                            UsageEventUtils.publishUsageEvent(etype, tmpTemplate.getAccountId(), tmpTemplateDataStore.getDataStoreId(), tmpTemplate.getId(), tmpTemplate.getName(), null, null,
                                    tmpTemplateDataStore.getPhysicalSize(), tmpTemplateDataStore.getSize(), VirtualMachineTemplate.class.getName(), tmpTemplate.getUuid());

                            if (s_logger.isDebugEnabled()) {
                                s_logger.debug("Template " + tmpTemplate.getUuid() + " uploaded successfully");
                            }
                            break;
                        case IN_PROGRESS:
                            if (tmpTemplate.getState() == VirtualMachineTemplate.State.NotUploaded) {
                                tmpTemplateDataStore.setDownloadState(VMTemplateStorageResourceAssoc.Status.DOWNLOAD_IN_PROGRESS);
                                stateMachine.transitTo(tmpTemplate, VirtualMachineTemplate.Event.UploadRequested, null, _templateDao);
                                tmpTemplateDataStore.setDownloadPercent(answer.getDownloadPercent());
                            } else if (tmpTemplate.getState() == VirtualMachineTemplate.State.UploadInProgress) { // check for timeout
                                if (System.currentTimeMillis() - tmpTemplateDataStore.getCreated().getTime() > _uploadOperationTimeout) {
                                    tmpTemplateDataStore.setDownloadState(VMTemplateStorageResourceAssoc.Status.DOWNLOAD_ERROR);
                                    tmpTemplateDataStore.setState(State.Failed);
                                    stateMachine.transitTo(tmpTemplate, VirtualMachineTemplate.Event.OperationFailed, null, _templateDao);
                                    msg = "Template " + tmpTemplate.getUuid() + " failed to upload due to operation timed out";
                                    s_logger.error(msg);
                                    sendAlert = true;
                                } else {
                                    tmpTemplateDataStore.setDownloadPercent(answer.getDownloadPercent());
                                }
                            }
                            break;
                        case ERROR:
                            tmpTemplateDataStore.setDownloadState(VMTemplateStorageResourceAssoc.Status.DOWNLOAD_ERROR);
                            tmpTemplateDataStore.setState(State.Failed);
                            stateMachine.transitTo(tmpTemplate, VirtualMachineTemplate.Event.OperationFailed, null, _templateDao);
                            msg = "Template " + tmpTemplate.getUuid() + " failed to upload. Error details: " + answer.getDetails();
                            s_logger.error(msg);
                            sendAlert = true;
                            break;
                        case UNKNOWN:
                            if (tmpTemplate.getState() == VirtualMachineTemplate.State.NotUploaded) { // check for timeout
                                if (System.currentTimeMillis() - tmpTemplateDataStore.getCreated().getTime() > _uploadOperationTimeout) {
                                    tmpTemplateDataStore.setDownloadState(VMTemplateStorageResourceAssoc.Status.ABANDONED);
                                    tmpTemplateDataStore.setState(State.Failed);
                                    stateMachine.transitTo(tmpTemplate, VirtualMachineTemplate.Event.OperationTimeout, null, _templateDao);
                                    msg = "Template " + tmpTemplate.getUuid() + " failed to upload due to operation timed out";
                                    s_logger.error(msg);
                                    sendAlert = true;
                                }
                            }
                            break;
                        }
                        _templateDataStoreDao.update(tmpTemplateDataStore.getId(), tmpTemplateDataStore);
                    } catch (NoTransitionException e) {
                        s_logger.error("Unexpected error " + e.getMessage());
                    } finally {
                        if (sendAlert) {
                            _alertMgr.sendAlert(AlertManager.AlertType.ALERT_TYPE_UPLOAD_FAILED,
                                    _vmTemplateZoneDao.listByTemplateId(tmpTemplate.getId()).get(0).getZoneId(), null, msg, msg);
                        }
                    }
                }

};