//,temp,DownloadMonitorImpl.java,203,258,temp,DownloadMonitorImpl.java,126,179
//,3
public class xxx {
    @Override
    public void downloadVolumeToStorage(DataObject volume, AsyncCompletionCallback<DownloadAnswer> callback) {
        boolean downloadJobExists = false;
        VolumeDataStoreVO volumeHost = null;
        DataStore store = volume.getDataStore();
        VolumeInfo volInfo = (VolumeInfo)volume;
        RegisterVolumePayload payload = (RegisterVolumePayload)volInfo.getpayload();
        String url = payload.getUrl();
        String checkSum = payload.getChecksum();
        ImageFormat format = ImageFormat.valueOf(payload.getFormat());

        volumeHost = _volumeStoreDao.findByStoreVolume(store.getId(), volume.getId());
        if (volumeHost == null) {
            volumeHost = new VolumeDataStoreVO(store.getId(), volume.getId(), new Date(), 0, Status.NOT_DOWNLOADED, null, null, "jobid0000", null, url, checkSum);
            _volumeStoreDao.persist(volumeHost);
        } else if ((volumeHost.getJobId() != null) && (volumeHost.getJobId().length() > 2)) {
            downloadJobExists = true;
        } else {
            // persit url and checksum
            volumeHost.setDownloadUrl(url);
            volumeHost.setChecksum(checkSum);
            _volumeStoreDao.update(volumeHost.getId(), volumeHost);
        }

        Long maxVolumeSizeInBytes = getMaxVolumeSizeInBytes();
        if (volumeHost != null) {
            start();
            Volume vol = _volumeDao.findById(volume.getId());
            DownloadCommand dcmd = new DownloadCommand((VolumeObjectTO)(volume.getTO()), maxVolumeSizeInBytes, checkSum, url, format);
            dcmd.setProxy(getHttpProxy());
            if (downloadJobExists) {
                dcmd = new DownloadProgressCommand(dcmd, volumeHost.getJobId(), RequestType.GET_OR_RESTART);
                dcmd.setResourceType(ResourceType.VOLUME);
            }

            EndPoint ep = _epSelector.select(volume);
            if (ep == null) {
                s_logger.warn("There is no secondary storage VM for image store " + store.getName());
                return;
            }
            DownloadListener dl = new DownloadListener(ep, store, volume, _timer, this, dcmd, callback);
            ComponentContext.inject(dl); // auto-wired those injected fields in DownloadListener

            if (downloadJobExists) {
                dl.setCurrState(volumeHost.getDownloadState());
            }

            try {
                ep.sendMessageAsync(dcmd, new UploadListener.Callback(ep.getId(), dl));
            } catch (Exception e) {
                s_logger.warn("Unable to start /resume download of volume " + volume.getId() + " to " + store.getName(), e);
                dl.setDisconnected();
                dl.scheduleStatusCheck(RequestType.GET_OR_RESTART);
            }
        }
    }

};