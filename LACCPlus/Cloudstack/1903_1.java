//,temp,TemplateServiceImpl.java,1168,1191,temp,VolumeServiceImpl.java,1632,1660
//,3
public class xxx {
    protected Void copyTemplateCrossZoneCallBack(AsyncCallbackDispatcher<TemplateServiceImpl, CreateCmdResult> callback, TemplateOpContext<TemplateApiResult> context) {
        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Performing copy template cross zone callback after completion");
        }
        TemplateInfo destTemplate = context.getTemplate();
        CreateCmdResult result = callback.getResult();
        AsyncCallFuture<TemplateApiResult> future = context.getFuture();
        TemplateApiResult res = new TemplateApiResult(destTemplate);
        try {
            if (result.isFailed()) {
                res.setResult(result.getResult());
                destTemplate.processEvent(Event.OperationFailed);
            } else {
                destTemplate.processEvent(Event.OperationSuccessed, result.getAnswer());
            }
            future.complete(res);
        } catch (Exception e) {
            s_logger.debug("Failed to process copy template cross zones callback", e);
            res.setResult(e.toString());
            future.complete(res);
        }

        return null;
    }

};