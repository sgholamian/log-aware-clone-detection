//,temp,BaseImageStoreDriverImpl.java,223,271,temp,BaseImageStoreDriverImpl.java,165,221
//,3
public class xxx {
    protected Void createTemplateAsyncCallback(AsyncCallbackDispatcher<? extends BaseImageStoreDriverImpl, DownloadAnswer> callback,
        CreateContext<CreateCmdResult> context) {
        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Performing image store createTemplate async callback");
        }
        DownloadAnswer answer = callback.getResult();
        DataObject obj = context.data;
        DataStore store = obj.getDataStore();

        TemplateDataStoreVO tmpltStoreVO = _templateStoreDao.findByStoreTemplate(store.getId(), obj.getId());
        if (tmpltStoreVO != null) {
            if (tmpltStoreVO.getDownloadState() == VMTemplateStorageResourceAssoc.Status.DOWNLOADED) {
                if (s_logger.isDebugEnabled()) {
                    s_logger.debug("Template is already in DOWNLOADED state, ignore further incoming DownloadAnswer");
                }
                return null;
            }
            TemplateDataStoreVO updateBuilder = _templateStoreDao.createForUpdate();
            updateBuilder.setDownloadPercent(answer.getDownloadPct());
            updateBuilder.setDownloadState(answer.getDownloadStatus());
            updateBuilder.setLastUpdated(new Date());
            updateBuilder.setErrorString(answer.getErrorString());
            updateBuilder.setJobId(answer.getJobId());
            updateBuilder.setLocalDownloadPath(answer.getDownloadPath());
            updateBuilder.setInstallPath(answer.getInstallPath());
            updateBuilder.setSize(answer.getTemplateSize());
            updateBuilder.setPhysicalSize(answer.getTemplatePhySicalSize());
            _templateStoreDao.update(tmpltStoreVO.getId(), updateBuilder);
            // update size in vm_template table
            VMTemplateVO tmlptUpdater = _templateDao.createForUpdate();
            tmlptUpdater.setSize(answer.getTemplateSize());
            _templateDao.update(obj.getId(), tmlptUpdater);
        }

        AsyncCompletionCallback<CreateCmdResult> caller = context.getParentCallback();

        if (answer.getDownloadStatus() == VMTemplateStorageResourceAssoc.Status.DOWNLOAD_ERROR ||
            answer.getDownloadStatus() == VMTemplateStorageResourceAssoc.Status.ABANDONED || answer.getDownloadStatus() == VMTemplateStorageResourceAssoc.Status.UNKNOWN) {
            CreateCmdResult result = new CreateCmdResult(null, null);
            result.setSuccess(false);
            result.setResult(answer.getErrorString());
            caller.complete(result);
            String msg = "Failed to register template: " + obj.getUuid() + " with error: " + answer.getErrorString();
            _alertMgr.sendAlert(AlertManager.AlertType.ALERT_TYPE_UPLOAD_FAILED, _vmTemplateZoneDao.listByTemplateId(obj.getId()).get(0).getZoneId(), null, msg, msg);
            s_logger.error(msg);
        } else if (answer.getDownloadStatus() == VMTemplateStorageResourceAssoc.Status.DOWNLOADED) {
            if (answer.getCheckSum() != null) {
                VMTemplateVO templateDaoBuilder = _templateDao.createForUpdate();
                templateDaoBuilder.setChecksum(answer.getCheckSum());
                _templateDao.update(obj.getId(), templateDaoBuilder);
            }

            CreateCmdResult result = new CreateCmdResult(null, null);
            caller.complete(result);
        }
        return null;
    }

};