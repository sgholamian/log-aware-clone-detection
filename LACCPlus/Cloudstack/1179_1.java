//,temp,TemplateServiceImpl.java,1256,1275,temp,VolumeServiceImpl.java,1241,1270
//,3
public class xxx {
    protected Void createDatadiskTemplateCallback(AsyncCallbackDispatcher<TemplateServiceImpl, CreateCmdResult> callback,
            CreateDataDiskTemplateContext<TemplateApiResult> context) {
        DataObject dataDiskTemplate = context.dataDiskTemplate;
        AsyncCallFuture<TemplateApiResult> future = context.getFuture();
        CreateCmdResult result = callback.getResult();
        TemplateApiResult dataDiskTemplateResult = new TemplateApiResult((TemplateObject)dataDiskTemplate);
        try {
            if (result.isSuccess()) {
                dataDiskTemplate.processEvent(Event.OperationSuccessed, result.getAnswer());
            } else {
                dataDiskTemplate.processEvent(Event.OperationFailed);
                dataDiskTemplateResult.setResult(result.getResult());
            }
        } catch (CloudRuntimeException e) {
            s_logger.debug("Failed to process create template callback", e);
            dataDiskTemplateResult.setResult(e.toString());
        }
        future.complete(dataDiskTemplateResult);
        return null;
    }

};