//,temp,TemplateServiceImpl.java,1146,1166,temp,SnapshotServiceImpl.java,545,568
//,3
public class xxx {
    protected Void copyTemplateCallBack(AsyncCallbackDispatcher<TemplateServiceImpl, CopyCommandResult> callback, TemplateOpContext<TemplateApiResult> context) {
        TemplateInfo destTemplate = context.getTemplate();
        CopyCommandResult result = callback.getResult();
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
            s_logger.debug("Failed to process copy template callback", e);
            res.setResult(e.toString());
            future.complete(res);
        }

        return null;
    }

};