//,temp,VmwareStorageProcessor.java,923,962,temp,VmwareStorageManagerImpl.java,473,513
//,3
public class xxx {
    @Override
    public Answer execute(VmwareHostService hostService, CopyVolumeCommand cmd) {
        Long volumeId = cmd.getVolumeId();
        String volumePath = cmd.getVolumePath();
        String secondaryStorageURL = cmd.getSecondaryStorageURL();
        String vmName = cmd.getVmName();

        VmwareContext context = hostService.getServiceContext(cmd);
        try {
            VmwareHypervisorHost hyperHost = hostService.getHyperHost(context, cmd);

            Pair<String, String> result;
            if (cmd.toSecondaryStorage()) {
                result = copyVolumeToSecStorage(hostService, hyperHost, cmd, vmName, volumeId, cmd.getPool().getUuid(), volumePath, secondaryStorageURL,
                        hostService.getWorkerName(context, cmd, 0), cmd.getNfsVersion());
            } else {
                StorageFilerTO poolTO = cmd.getPool();

                ManagedObjectReference morDatastore = HypervisorHostHelper.findDatastoreWithBackwardsCompatibility(hyperHost, poolTO.getUuid());
                if (morDatastore == null) {
                    morDatastore = hyperHost.mountDatastore(false, poolTO.getHost(), 0, poolTO.getPath(), poolTO.getUuid().replace("-", ""));

                    if (morDatastore == null) {
                        throw new Exception("Unable to mount storage pool on host. storeUrl: " + poolTO.getHost() + ":/" + poolTO.getPath());
                    }
                }

                result = copyVolumeFromSecStorage(hyperHost, volumeId, new DatastoreMO(context, morDatastore), secondaryStorageURL, volumePath, cmd.getNfsVersion());
                deleteVolumeDirOnSecondaryStorage(volumeId, secondaryStorageURL, cmd.getNfsVersion());
            }
            return new CopyVolumeAnswer(cmd, true, null, result.first(), result.second());
        } catch (Throwable e) {
            if (e instanceof RemoteException) {
                hostService.invalidateServiceContext(context);
            }

            String msg = "Unable to execute CopyVolumeCommand due to exception";
            s_logger.error(msg, e);
            return new CopyVolumeAnswer(cmd, false, "CopyVolumeCommand failed due to exception: " + StringUtils.getExceptionStackInfo(e), null, null);
        }
    }

};