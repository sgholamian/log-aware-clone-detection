//,temp,TemplateServiceImpl.java,967,988,temp,SnapshotServiceImpl.java,545,568
//,3
public class xxx {
    protected Void syncTemplateCallBack(AsyncCallbackDispatcher<TemplateServiceImpl, CopyCommandResult> callback, TemplateOpContext<TemplateApiResult> context) {
        TemplateInfo destTemplate = context.getTemplate();
        CopyCommandResult result = callback.getResult();
        AsyncCallFuture<TemplateApiResult> future = context.getFuture();
        TemplateApiResult res = new TemplateApiResult(destTemplate);
        try {
            if (result.isFailed()) {
                res.setResult(result.getResult());
                // no change to existing template_store_ref, will try to re-sync later if other call triggers this sync operation, like copy template
            } else {
                // this will update install path properly, next time it will not sync anymore.
                destTemplate.processEvent(Event.OperationSuccessed, result.getAnswer());
            }
            future.complete(res);
        } catch (Exception e) {
            s_logger.debug("Failed to process sync template callback", e);
            res.setResult(e.toString());
            future.complete(res);
        }

        return null;
    }

};