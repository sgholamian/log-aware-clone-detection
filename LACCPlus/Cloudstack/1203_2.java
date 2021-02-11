//,temp,VmwareStorageProcessor.java,1023,1052,temp,VmwareStorageProcessor.java,923,962
//,3
public class xxx {
    @Override
    public Answer copyVolumeFromImageCacheToPrimary(CopyCommand cmd) {
        VolumeObjectTO srcVolume = (VolumeObjectTO)cmd.getSrcTO();
        VolumeObjectTO destVolume = (VolumeObjectTO)cmd.getDestTO();
        VmwareContext context = hostService.getServiceContext(cmd);
        try {

            NfsTO srcStore = (NfsTO)srcVolume.getDataStore();
            DataStoreTO destStore = destVolume.getDataStore();

            VmwareHypervisorHost hyperHost = hostService.getHyperHost(context, cmd);
            String uuid = destStore.getUuid();

            ManagedObjectReference morDatastore = HypervisorHostHelper.findDatastoreWithBackwardsCompatibility(hyperHost, uuid);
            if (morDatastore == null) {
                URI uri = new URI(destStore.getUrl());

                morDatastore = hyperHost.mountDatastore(false, uri.getHost(), 0, uri.getPath(), destStore.getUuid().replace("-", ""));

                if (morDatastore == null) {
                    throw new Exception("Unable to mount storage pool on host. storeUrl: " + uri.getHost() + ":/" + uri.getPath());
                }
            }

            Pair<String, String> result = copyVolumeFromSecStorage(hyperHost, srcVolume.getPath(), new DatastoreMO(context, morDatastore), srcStore.getUrl(), (long)cmd.getWait() * 1000, _nfsVersion);
            deleteVolumeDirOnSecondaryStorage(result.first(), srcStore.getUrl(), _nfsVersion);
            VolumeObjectTO newVolume = new VolumeObjectTO();
            newVolume.setPath(result.second());
            return new CopyCmdAnswer(newVolume);
        } catch (Throwable t) {
            if (t instanceof RemoteException) {
                hostService.invalidateServiceContext(context);
            }

            String msg = "Unable to execute CopyVolumeCommand due to exception";
            s_logger.error(msg, t);
            return new CopyCmdAnswer("copy volume secondary to primary failed due to exception: " + VmwareHelper.getExceptionMessage(t));
        }

    }

};