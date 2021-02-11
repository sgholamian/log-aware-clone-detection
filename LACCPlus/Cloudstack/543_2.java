//,temp,UploadMonitorImpl.java,173,207,temp,UploadMonitorImpl.java,144,171
//,3
public class xxx {
    @Override
    public void extractVolume(UploadVO uploadVolumeObj, DataStore secStore, VolumeVO volume, String url, Long dataCenterId, String installPath, long eventId,
        long asyncJobId, AsyncJobManager asyncMgr) {

        uploadVolumeObj.setUploadState(Upload.Status.NOT_UPLOADED);
        _uploadDao.update(uploadVolumeObj.getId(), uploadVolumeObj);

        start();
        UploadCommand ucmd = new UploadCommand(url, volume.getId(), volume.getSize(), installPath, Type.VOLUME);
        UploadListener ul =
            new UploadListener(secStore, _timer, _uploadDao, uploadVolumeObj, this, ucmd, volume.getAccountId(), volume.getName(), Type.VOLUME, eventId, asyncJobId,
                asyncMgr);
        _listenerMap.put(uploadVolumeObj, ul);

        try {
            EndPoint ep = _epSelector.select(secStore);
            if (ep == null) {
                String errMsg = "No remote endpoint to send command, check if host or ssvm is down?";
                s_logger.error(errMsg);
                return;
            }
            ep.sendMessageAsync(ucmd, new UploadListener.Callback(ep.getId(), ul));
        } catch (Exception e) {
            s_logger.warn("Unable to start upload of volume " + volume.getName() + " from " + secStore.getName() + " to " + url, e);
            ul.setDisconnected();
            ul.scheduleStatusCheck(RequestType.GET_OR_RESTART);
        }
    }

};