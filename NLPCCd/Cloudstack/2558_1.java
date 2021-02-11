//,temp,sample_5690.java,2,17,temp,sample_5689.java,2,17
//,3
public class xxx {
public void dummy_method(){
try {
destVolume = (VolumeInfo)destStore.create(srcVolume);
srcVolume.processEvent(Event.MigrationRequested);
destVolume.processEventOnly(Event.CreateOnlyRequested);
CopyVolumeContext<VolumeApiResult> context = new CopyVolumeContext<VolumeApiResult>(null, future, srcVolume, destVolume, destStore);
AsyncCallbackDispatcher<VolumeServiceImpl, CopyCommandResult> caller = AsyncCallbackDispatcher.create(this);
caller.setCallback(caller.getTarget().copyVolumeFromPrimaryToImageCallback(null, null)).setContext(context);
motionSrv.copyAsync(srcVolume, destVolume, caller);
return future;
} catch (Exception e) {


log.info("failed to copy volume to image store");
}
}

};