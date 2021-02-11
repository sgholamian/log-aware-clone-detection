//,temp,DownloadMonitorImpl.java,203,258,temp,DownloadMonitorImpl.java,126,179
//,3
public class xxx {
    private void initiateTemplateDownload(DataObject template, AsyncCompletionCallback<DownloadAnswer> callback) {
        boolean downloadJobExists = false;
        TemplateDataStoreVO vmTemplateStore = null;
        DataStore store = template.getDataStore();

        vmTemplateStore = _vmTemplateStoreDao.findByStoreTemplate(store.getId(), template.getId());
        if (vmTemplateStore == null) {
            vmTemplateStore =
                new TemplateDataStoreVO(store.getId(), template.getId(), new Date(), 0, Status.NOT_DOWNLOADED, null, null, "jobid0000", null, template.getUri());
            vmTemplateStore.setDataStoreRole(store.getRole());
            vmTemplateStore = _vmTemplateStoreDao.persist(vmTemplateStore);
        } else if ((vmTemplateStore.getJobId() != null) && (vmTemplateStore.getJobId().length() > 2)) {
            downloadJobExists = true;
        }

        Long maxTemplateSizeInBytes = getMaxTemplateSizeInBytes();
        if (vmTemplateStore != null) {
            start();
            VirtualMachineTemplate tmpl = _templateDao.findById(template.getId());
            DownloadCommand dcmd = new DownloadCommand((TemplateObjectTO)(template.getTO()), maxTemplateSizeInBytes);
            dcmd.setProxy(getHttpProxy());
            if (downloadJobExists) {
                dcmd = new DownloadProgressCommand(dcmd, vmTemplateStore.getJobId(), RequestType.GET_OR_RESTART);
            }
            if (vmTemplateStore.isCopy()) {
                dcmd.setCreds(TemplateConstants.DEFAULT_HTTP_AUTH_USER, _copyAuthPasswd);
            }
            EndPoint ep = _epSelector.select(template);
            if (ep == null) {
                String errMsg = "There is no secondary storage VM for downloading template to image store " + store.getName();
                s_logger.warn(errMsg);
                throw new CloudRuntimeException(errMsg);
            }
            DownloadListener dl = new DownloadListener(ep, store, template, _timer, this, dcmd, callback);
            ComponentContext.inject(dl);  // initialize those auto-wired field in download listener.
            if (downloadJobExists) {
                // due to handling existing download job issues, we still keep
                // downloadState in template_store_ref to avoid big change in
                // DownloadListener to use
                // new ObjectInDataStore.State transition. TODO: fix this later
                // to be able to remove downloadState from template_store_ref.
                s_logger.info("found existing download job");
                dl.setCurrState(vmTemplateStore.getDownloadState());
            }

            try {
                ep.sendMessageAsync(dcmd, new UploadListener.Callback(ep.getId(), dl));
            } catch (Exception e) {
                s_logger.warn("Unable to start /resume download of template " + template.getId() + " to " + store.getName(), e);
                dl.setDisconnected();
                dl.scheduleStatusCheck(RequestType.GET_OR_RESTART);
            }
        }
    }

};