//,temp,VmwareStorageProcessor.java,1023,1052,temp,VmwareStorageProcessor.java,923,962
//,3
public class xxx {
    @Override
    public Answer copyVolumeFromPrimaryToSecondary(CopyCommand cmd) {
        VolumeObjectTO srcVolume = (VolumeObjectTO)cmd.getSrcTO();
        VolumeObjectTO destVolume = (VolumeObjectTO)cmd.getDestTO();
        String vmName = srcVolume.getVmName();

        VmwareContext context = hostService.getServiceContext(cmd);
        try {
            DataStoreTO primaryStorage = srcVolume.getDataStore();
            NfsTO destStore = (NfsTO)destVolume.getDataStore();
            VmwareHypervisorHost hyperHost = hostService.getHyperHost(context, cmd);

            Pair<String, String> result;

            result =
                    copyVolumeToSecStorage(hostService, hyperHost, cmd, vmName, primaryStorage.getUuid(), srcVolume.getPath(), destVolume.getPath(), destStore.getUrl(),
                            hostService.getWorkerName(context, cmd, 0));
            VolumeObjectTO newVolume = new VolumeObjectTO();
            newVolume.setPath(result.first() + File.separator + result.second());
            return new CopyCmdAnswer(newVolume);
        } catch (Throwable e) {
            if (e instanceof RemoteException) {
                hostService.invalidateServiceContext(context);
            }

            String msg = "Unable to execute CopyVolumeCommand due to exception";
            s_logger.error(msg, e);
            return new CopyCmdAnswer("copy volume from primary to secondary failed due to exception: " + VmwareHelper.getExceptionMessage(e));
        }
    }

};