//,temp,TemplateServiceImpl.java,967,988,temp,VolumeServiceImpl.java,1821,1852
//,3
public class xxx {
    protected Void resizeVolumeCallback(AsyncCallbackDispatcher<VolumeServiceImpl, CreateCmdResult> callback, CreateVolumeContext<VolumeApiResult> context) {
        CreateCmdResult result = callback.getResult();
        AsyncCallFuture<VolumeApiResult> future = context.future;
        VolumeInfo volume = (VolumeInfo)context.volume;

        if (result.isFailed()) {
            try {
                volume.processEvent(Event.OperationFailed);
            } catch (Exception e) {
                s_logger.debug("Failed to change state", e);
            }
            VolumeApiResult res = new VolumeApiResult(volume);
            res.setResult(result.getResult());
            future.complete(res);
            return null;
        }

        try {
            volume.processEvent(Event.OperationSuccessed);
        } catch (Exception e) {
            s_logger.debug("Failed to change state", e);
            VolumeApiResult res = new VolumeApiResult(volume);
            res.setResult(result.getResult());
            future.complete(res);
            return null;
        }

        VolumeApiResult res = new VolumeApiResult(volume);
        future.complete(res);

        return null;
    }

};