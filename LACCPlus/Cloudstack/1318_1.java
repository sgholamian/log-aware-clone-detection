//,temp,BaseImageStoreDriverImpl.java,223,271,temp,BaseImageStoreDriverImpl.java,165,221
//,3
public class xxx {
    protected Void
        createVolumeAsyncCallback(AsyncCallbackDispatcher<? extends BaseImageStoreDriverImpl, DownloadAnswer> callback, CreateContext<CreateCmdResult> context) {
        DownloadAnswer answer = callback.getResult();
        DataObject obj = context.data;
        DataStore store = obj.getDataStore();

        VolumeDataStoreVO volStoreVO = _volumeStoreDao.findByStoreVolume(store.getId(), obj.getId());
        if (volStoreVO != null) {
            if (volStoreVO.getDownloadState() == VMTemplateStorageResourceAssoc.Status.DOWNLOADED) {
                if (s_logger.isDebugEnabled()) {
                    s_logger.debug("Volume is already in DOWNLOADED state, ignore further incoming DownloadAnswer");
                }
                return null;
            }
            VolumeDataStoreVO updateBuilder = _volumeStoreDao.createForUpdate();
            updateBuilder.setDownloadPercent(answer.getDownloadPct());
            updateBuilder.setDownloadState(answer.getDownloadStatus());
            updateBuilder.setLastUpdated(new Date());
            updateBuilder.setErrorString(answer.getErrorString());
            updateBuilder.setJobId(answer.getJobId());
            updateBuilder.setLocalDownloadPath(answer.getDownloadPath());
            updateBuilder.setInstallPath(answer.getInstallPath());
            updateBuilder.setSize(answer.getTemplateSize());
            updateBuilder.setPhysicalSize(answer.getTemplatePhySicalSize());
            _volumeStoreDao.update(volStoreVO.getId(), updateBuilder);
            // update size in volume table
            VolumeVO volUpdater = volumeDao.createForUpdate();
            volUpdater.setSize(answer.getTemplateSize());
            volumeDao.update(obj.getId(), volUpdater);
        }

        AsyncCompletionCallback<CreateCmdResult> caller = context.getParentCallback();

        if (answer.getDownloadStatus() == VMTemplateStorageResourceAssoc.Status.DOWNLOAD_ERROR ||
            answer.getDownloadStatus() == VMTemplateStorageResourceAssoc.Status.ABANDONED || answer.getDownloadStatus() == VMTemplateStorageResourceAssoc.Status.UNKNOWN) {
            CreateCmdResult result = new CreateCmdResult(null, null);
            result.setSuccess(false);
            result.setResult(answer.getErrorString());
            caller.complete(result);
            String msg = "Failed to upload volume: " + obj.getUuid() + " with error: " + answer.getErrorString();
            _alertMgr.sendAlert(AlertManager.AlertType.ALERT_TYPE_UPLOAD_FAILED,
                    (volStoreVO == null ? -1L : volStoreVO.getZoneId()), null, msg, msg);
            s_logger.error(msg);
        } else if (answer.getDownloadStatus() == VMTemplateStorageResourceAssoc.Status.DOWNLOADED) {
            CreateCmdResult result = new CreateCmdResult(null, null);
            caller.complete(result);
        }
        return null;
    }

};