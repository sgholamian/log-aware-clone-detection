//,temp,CloudStackPrimaryDataStoreDriverImpl.java,212,239,temp,BaseImageStoreDriverImpl.java,341,364
//,3
public class xxx {
    @Override
    public Void createDataDiskTemplateAsync(TemplateInfo dataDiskTemplate, String path, String diskId, boolean bootable, long fileSize, AsyncCompletionCallback<CreateCmdResult> callback) {
        Answer answer = null;
        String errMsg = null;
        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Create Datadisk template: " + dataDiskTemplate.getId());
        }
        CreateDatadiskTemplateCommand cmd = new CreateDatadiskTemplateCommand(dataDiskTemplate.getTO(), path, diskId, fileSize, bootable);
        EndPoint ep = _defaultEpSelector.select(dataDiskTemplate.getDataStore());
        if (ep == null) {
            errMsg = "No remote endpoint to send command, check if host or ssvm is down?";
            s_logger.error(errMsg);
            answer = new Answer(cmd, false, errMsg);
        } else {
            answer = ep.sendMessage(cmd);
        }
        if (answer != null && !answer.getResult()) {
            errMsg = answer.getDetails();
        }
        CreateCmdResult result = new CreateCmdResult(null, answer);
        result.setResult(errMsg);
        callback.complete(result);
        return null;
    }

};