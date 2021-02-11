//,temp,UploadMonitorImpl.java,173,207,temp,UploadMonitorImpl.java,144,171
//,3
public class xxx {
    @Override
    public Long extractTemplate(VMTemplateVO template, String url, TemplateDataStoreVO vmTemplateHost, Long dataCenterId, long eventId, long asyncJobId,
        AsyncJobManager asyncMgr) {

        Type type = (template.getFormat() == ImageFormat.ISO) ? Type.ISO : Type.TEMPLATE;

        DataStore secStore = storeMgr.getImageStore(dataCenterId);

        UploadVO uploadTemplateObj = new UploadVO(secStore.getId(), template.getId(), new Date(), Upload.Status.NOT_UPLOADED, type, url, Mode.FTP_UPLOAD);
        _uploadDao.persist(uploadTemplateObj);

        if (vmTemplateHost != null) {
            start();
            UploadCommand ucmd = new UploadCommand(template, url, vmTemplateHost.getInstallPath(), vmTemplateHost.getSize());
            UploadListener ul =
                new UploadListener(secStore, _timer, _uploadDao, uploadTemplateObj, this, ucmd, template.getAccountId(), template.getName(), type, eventId, asyncJobId,
                    asyncMgr);
            _listenerMap.put(uploadTemplateObj, ul);
            try {
                EndPoint ep = _epSelector.select(secStore);
                if (ep == null) {
                    String errMsg = "No remote endpoint to send command, check if host or ssvm is down?";
                    s_logger.error(errMsg);
                    return null;
                }
                ep.sendMessageAsync(ucmd, new UploadListener.Callback(ep.getId(), ul));
            } catch (Exception e) {
                s_logger.warn("Unable to start upload of " + template.getUniqueName() + " from " + secStore.getName() + " to " + url, e);
                ul.setDisconnected();
                ul.scheduleStatusCheck(RequestType.GET_OR_RESTART);
            }
            return uploadTemplateObj.getId();
        }
        return null;
    }

};