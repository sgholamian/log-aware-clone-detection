//,temp,VolumeServiceImpl.java,1764,1791,temp,VolumeServiceImpl.java,1529,1553
//,3
public class xxx {
    @Override
    public AsyncCallFuture<VolumeApiResult> resize(VolumeInfo volume) {
        AsyncCallFuture<VolumeApiResult> future = new AsyncCallFuture<VolumeApiResult>();
        VolumeApiResult result = new VolumeApiResult(volume);
        try {
            volume.processEvent(Event.ResizeRequested);
        } catch (Exception e) {
            s_logger.debug("Failed to change state to resize", e);
            result.setResult(e.toString());
            future.complete(result);
            return future;
        }
        CreateVolumeContext<VolumeApiResult> context = new CreateVolumeContext<VolumeApiResult>(null, volume, future);
        AsyncCallbackDispatcher<VolumeServiceImpl, CreateCmdResult> caller = AsyncCallbackDispatcher.create(this);
        caller.setCallback(caller.getTarget().resizeVolumeCallback(caller, context)).setContext(context);

        try {
            volume.getDataStore().getDriver().resize(volume, caller);
        } catch (Exception e) {
            s_logger.debug("Failed to change state to resize", e);

            result.setResult(e.toString());

            future.complete(result);
        }

        return future;
    }

};